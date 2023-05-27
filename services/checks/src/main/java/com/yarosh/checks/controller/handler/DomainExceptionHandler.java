package com.yarosh.checks.controller.handler;

import com.yarosh.checks.domain.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DomainExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DomainExceptionHandler.class);

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

    @ExceptionHandler(InvalidCashierException.class)
    protected ResponseEntity<String> handleInvalidCashierException(final InvalidCashierException e) {
        LOGGER.debug("InvalidCashierException was handled, message {}", e.getMessage());
        LOGGER.trace("InvalidCashierException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(InvalidCheckException.class)
    protected ResponseEntity<String> handleInvalidCheckException(final InvalidCheckException e) {
        LOGGER.debug("InvalidCheckException was handled, message {}", e.getMessage());
        LOGGER.trace("InvalidCheckException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(TypedIdException.class)
    protected ResponseEntity<String> handleInvalidTypedIdException(final TypedIdException e) {
        LOGGER.debug("TypedIdException was handled, message {}", e.getMessage());
        LOGGER.trace("TypedIdException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}
