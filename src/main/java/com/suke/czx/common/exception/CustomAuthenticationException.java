package com.suke.czx.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author czx
 */
public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String msg) {
        super(msg);
    }
}
