package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record CheckId(Long value) implements DomainId {

    public CheckId(Long value) {
        this.value = value;
        validate();
    }

    @Override
    public String toString() {
        return "CheckId{" +
                "value=" + value +
                '}';
    }

    private void validate() {
        if (value == null) {
            throw new TypedIdException(this);
        }
    }
}
