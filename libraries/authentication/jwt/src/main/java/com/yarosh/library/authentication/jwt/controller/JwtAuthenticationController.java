package com.yarosh.library.authentication.jwt.controller;

import com.yarosh.library.authentication.jwt.controller.dto.AuthenticationDto;
import com.yarosh.library.authentication.jwt.controller.view.JwtResponseView;
import com.yarosh.library.authentication.jwt.domain.JwtResponse;
import com.yarosh.library.authentication.jwt.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/auth")
public class JwtAuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationController.class);

    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseView> login(@RequestBody AuthenticationDto dto) {
        LOGGER.debug("Basic login started, credentials: {}", dto);
        final JwtResponseView view = convertToJwtResponseView(jwtTokenService.login(dto.username(), dto.password()));
        LOGGER.debug("JWT token generated, response: {}", view);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    private JwtResponseView convertToJwtResponseView(JwtResponse jwtResponse) {
        return new JwtResponseView(jwtResponse.username(), jwtResponse.token());
    }
}
