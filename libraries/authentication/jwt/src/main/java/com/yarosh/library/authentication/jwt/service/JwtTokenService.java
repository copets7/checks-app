package com.yarosh.library.authentication.jwt.service;

import com.yarosh.library.authentication.jwt.domain.JwtResponse;
import com.yarosh.library.user.domain.Role;
import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.MessageFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class JwtTokenService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);

    private static final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;

    private final String secret;
    private final long validityInMilliSeconds;

    private final UserDetailsService userDetailsService;
    private final UserService<User> userService;


    public JwtTokenService(AuthenticationManager authenticationManager,
                           String secret,
                           long validityInMilliSeconds,
                           UserDetailsService userDetailsService,
                           UserService<User> userService) {
        this.authenticationManager = authenticationManager;
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.validityInMilliSeconds = validityInMilliSeconds;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    public JwtResponse login(String username, String password) {
        authenticate(username, password);
        return userService.findByUsername(username)
                .map(user -> createToken(user.username(), user.roles()))
                .map(token -> new JwtResponse(username, token))
                .orElseThrow(() -> new UsernameNotFoundException(MessageFormat.format("User with username {0} not found", username)));
    }

    public String createToken(String username, List<Role> roles) {
        final Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles.stream().map(Role::getName).toList());
        final Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityInMilliSeconds))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()); //Maybe " " can be replaced with null
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<String> extractToken(String bearerToken) {
        return Optional.ofNullable(bearerToken)
                .filter(token -> token != null && token.startsWith(TOKEN_PREFIX))
                .map(token -> token.substring(TOKEN_PREFIX.length()));
    }

    public boolean validateToken(String token) {
        try {
            final Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.debug("JWT TOKEN is expired or invalid", e);
            throw new JwtAuthenticationException("JWT TOKEN is expired or invalid");
        }
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            LOGGER.debug("Invalid credentials, username: {}", username, e);
            throw new JwtAuthenticationException("Invalid credentials were passed");
        }
    }
}
