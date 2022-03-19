package com.suke.czx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.suke.czx.common.exception.CustomAuthenticationException;
import com.suke.czx.common.utils.Constant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author czx
 */
@Slf4j
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final StringRedisTemplate redisTemplate;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    public ValidateCodeFilter(StringRedisTemplate redisTemplate,
            AuthenticationFailureHandler authenticationFailureHandler) {
        this.redisTemplate = redisTemplate;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @SuppressWarnings("NullableProblems")
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String url = request.getRequestURI();
        if (pathMatcher.match(Constant.TOKEN_ENTRY_POINT_URL, url)) {
            String captcha = request.getParameter("captcha");
            String randomStr = request.getParameter("randomStr");

            if (StrUtil.isBlank(captcha) || StrUtil.isBlank(randomStr)) {
                CustomAuthenticationException exception = new CustomAuthenticationException("验证码为空");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }

            String code_key = redisTemplate.opsForValue().get(Constant.NUMBER_CODE_KEY + randomStr);
            if (StrUtil.isEmpty(code_key)) {
                CustomAuthenticationException exception = new CustomAuthenticationException("验证码过期");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }

            if (!captcha.equalsIgnoreCase(code_key)) {
                CustomAuthenticationException exception = new CustomAuthenticationException("验证码不正确");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
