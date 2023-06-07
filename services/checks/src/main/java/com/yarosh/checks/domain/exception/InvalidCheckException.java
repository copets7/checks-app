package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidCheckException extends RuntimeException {

    public InvalidCheckException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }

    public static InvalidCheckException invalidPrice(Double invalidPrice, Double price) {
        return new InvalidCheckException("Total price in check is less than {0} or null, price: {1}", invalidPrice, price);
    }
}
