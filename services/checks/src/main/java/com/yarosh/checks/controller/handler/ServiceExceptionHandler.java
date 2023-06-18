package com.yarosh.checks.controller.handler;

import com.yarosh.checks.service.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<String> handleProductNotFoundException(final ObjectNotFoundException e) {
        LOGGER.debug("ObjectNotFoundException was handled, message {}", e.getMessage());
        LOGGER.trace("ObjectNotFoundException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }
}
