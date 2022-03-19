package com.suke.czx.authentication.handler;

import com.suke.czx.common.utils.Constant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author czx
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutHandler {

    private final StringRedisTemplate redisTemplate;

    public CustomLogoutSuccessHandler(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(Constant.TOKEN);
        redisTemplate.delete(Constant.AUTHENTICATION_TOKEN + token);
        redisTemplate.delete(token);
    }
}
