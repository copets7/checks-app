package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record ProductId(Long value) implements DomainId {

    public ProductId(Long value) {
        this.value = value;
        validate();
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "value=" + value +
                '}';
    }

    private void validate() {
        if (value == null) {
            throw new TypedIdException(this);
        }
    }
}
