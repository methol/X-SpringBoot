package com.suke.czx.authentication.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.authentication.detail.CustomUserDetailsUser;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 *
 **/
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CustomAuthenticationSuccessHandler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) {
        String token;
        Long userId = 0L;
        if (authentication.getPrincipal() instanceof CustomUserDetailsUser userDetailsUser) {
            token = SecureUtil.md5(userDetailsUser.getUsername() + System.currentTimeMillis());
            userId = userDetailsUser.getUserId();
        } else {
            token = SecureUtil.md5(String.valueOf(System.currentTimeMillis()));
        }
        redisTemplate.opsForValue()
                .set(Constant.AUTHENTICATION_TOKEN + token, token, Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(token, String.valueOf(userId), Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);

        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(R.ok().put(Constant.TOKEN, token)));
    }
}
