package com.suke.czx.authentication;

import com.suke.czx.authentication.detail.CustomUserDetailsService;
import com.suke.czx.authentication.handler.CustomAuthenticationFailHandler;
import com.suke.czx.authentication.handler.CustomAuthenticationSuccessHandler;
import com.suke.czx.authentication.handler.CustomLogoutSuccessHandler;
import com.suke.czx.authentication.handler.TokenAuthenticationFailHandler;
import com.suke.czx.authentication.provider.CustomDaoAuthenticationProvider;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.config.AuthIgnoreConfig;
import com.suke.czx.interceptor.AuthenticationTokenFilter;
import com.suke.czx.interceptor.ValidateCodeFilter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.CorsFilter;

import java.util.HashSet;
import java.util.Set;

/**
 *
 **/
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService customUserDetailsService;
    private final StringRedisTemplate redisTemplate;
    private final AuthIgnoreConfig authIgnoreConfig;
    private final CorsFilter corsFilter;

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {
        // 允许不需要认证的接口
        Set<String> permitAll = new HashSet<>(authIgnoreConfig.getIgnoreUrls());

        permitAll.add("/actuator/**");
        permitAll.add("/error");

        // openapi docs
        permitAll.add("/swagger-ui.html");
        permitAll.add("/swagger-ui/**");
        permitAll.add("/v3/api-docs/**");

        permitAll.add(Constant.TOKEN_ENTRY_POINT_URL);

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
                = http.authorizeRequests();
        registry.antMatchers(permitAll.toArray(String[]::new)).permitAll()
                .anyRequest().authenticated().and()
                .csrf().disable();
        http
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()

                .formLogin()
                .loginProcessingUrl(Constant.TOKEN_ENTRY_POINT_URL)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and()

                .logout()
                .logoutUrl(Constant.TOKEN_LOGOUT_URL)
                .addLogoutHandler(logoutHandler())
                .logoutSuccessUrl("/sys/logout")
                .permitAll()
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(new TokenAuthenticationFailHandler())
                .and()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                // 如果不用验证码，注释这个过滤器即可
                .addFilterBefore(new ValidateCodeFilter(redisTemplate, authenticationFailureHandler()),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilter(new AuthenticationTokenFilter(authenticationManagerBean(), redisTemplate,
                        customUserDetailsService))
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailHandler();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new CustomLogoutSuccessHandler(redisTemplate);
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(redisTemplate);
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
