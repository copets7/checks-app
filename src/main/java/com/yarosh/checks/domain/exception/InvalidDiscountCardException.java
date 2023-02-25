package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidDiscountCardException extends RuntimeException {

    public InvalidDiscountCardException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
