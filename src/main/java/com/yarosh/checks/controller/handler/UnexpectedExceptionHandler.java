package com.yarosh.checks.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UnexpectedExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnexpectedExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<String> handleRuntimeException(final RuntimeException e) {
        LOGGER.debug("RuntimeException was handled, message {}", e.getMessage());
        LOGGER.trace("RuntimeException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
