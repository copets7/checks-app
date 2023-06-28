package com.yarosh.library.authentication.jwt;

import com.yarosh.library.authentication.jwt.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final JwtTokenService jwtTokenService;

    public JwtTokenFilter(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        jwtTokenService.extractToken(((HttpServletRequest) servletRequest).getHeader(AUTHORIZATION_HEADER))
                .filter(this::validateToken)
                .map(jwtTokenService::getAuthentication)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean validateToken(String token) {
        final boolean isValid = jwtTokenService.validateToken(token);
        if (!isValid) {
            LOGGER.debug("JWT token validation failed");
        }
        return isValid;
    }
}
