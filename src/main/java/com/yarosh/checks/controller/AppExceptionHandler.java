package com.yarosh.checks.controller;

import com.yarosh.checks.domain.exception.InvalidDiscountCardException;
import com.yarosh.checks.domain.exception.InvalidProductException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppExceptionHandler.class);

    @ExceptionHandler(InvalidProductException.class)
    protected ResponseEntity<String> handleInvalidProductException(final InvalidProductException e) {
        LOGGER.debug("InvalidProductException was handled, message {}", e.getMessage());
        LOGGER.trace("InvalidProductException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(InvalidDiscountCardException.class)
    protected ResponseEntity<String> handleInvalidDiscountCardException(final InvalidDiscountCardException e) {
        LOGGER.debug("InvalidDiscountCardException was handled, message {}", e.getMessage());
        LOGGER.trace("InvalidDiscountCardException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}
