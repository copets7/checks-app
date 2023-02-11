package com.yarosh.checks.service.check;

import java.text.MessageFormat;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(long id) {
        this("Product was not found, id: {0}", id);
    }

    public ProductNotFoundException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
