package com.yarosh.checks.service;

import java.text.MessageFormat;
import java.util.function.Supplier;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(long id, String name) {
        this("{0} was not found, value: {1}", name, id);
    }

    public ObjectNotFoundException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }

    public static Supplier<ObjectNotFoundException> supplier(long id, String name) {
        return () -> new ObjectNotFoundException(id, name);
    }
}
