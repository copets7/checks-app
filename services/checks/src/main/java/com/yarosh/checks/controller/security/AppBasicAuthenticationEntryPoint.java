package com.yarosh.checks.controller.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

public class AppBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppBasicAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        LOGGER.debug("Basic authentication failed", authException);
        LOGGER.trace("Basic authentication failed, HttpRequest: {}", request);
        response.addHeader("WWW-Authenticate", MessageFormat.format("Basic realm = {0}", getRealmName()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    public void afterPropertiesSet() {
       setRealmName(this.getClass().getName());
       super.afterPropertiesSet();
    }
}
