package com.yarosh.checks.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.inject.Inject;

@ControllerAdvice
public class UnexpectedExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnexpectedExceptionHandler.class);

    private final String internalServerErrorMessage;

    @Inject
    public UnexpectedExceptionHandler(final @Value("${error.internal.server.message}") String internalServerErrorMessage) {
        this.internalServerErrorMessage = internalServerErrorMessage;
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<String> handleRuntimeException(final RuntimeException e) {
        LOGGER.error("RuntimeException was handled, message {}", e.getMessage());
        LOGGER.debug("RuntimeException was handled", e);
        return new ResponseEntity<>(internalServerErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
