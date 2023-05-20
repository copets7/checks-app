package com.yarosh.checks.controller.handler;

import com.yarosh.checks.repository.exception.RepositoryException;
import com.yarosh.checks.repository.exception.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RepositoryExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryExceptionHandler.class);

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "The server encountered an error and could not complete you request";

    @ExceptionHandler(RecordNotFoundException.class)
    protected ResponseEntity<String> handleRecordNotFoundException(final RecordNotFoundException e) {
        LOGGER.debug("RecordNotFoundException was handled, message {}", e.getMessage());
        LOGGER.trace("RecordNotFoundException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(RepositoryException.class)
    protected ResponseEntity<String> handleRepositoryException(final RepositoryException e) {
        LOGGER.error("RepositoryException was handled, message {}", e.getMessage());
        LOGGER.debug("RepositoryException was handled", e);
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<String> handleSQLException(final SQLException e) {
        LOGGER.error("SQLException was handled, message {}", e.getMessage());
        LOGGER.debug("SQLException was handled", e);
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
