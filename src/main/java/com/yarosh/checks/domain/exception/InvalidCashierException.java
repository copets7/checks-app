package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidCashierException extends RuntimeException {

    public InvalidCashierException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
