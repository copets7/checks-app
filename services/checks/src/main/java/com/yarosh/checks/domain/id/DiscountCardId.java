package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record DiscountCardId(Long value) implements DomainId {

    public DiscountCardId(Long value) {
        this.value = value;
        validate();
    }

    @Override
    public String toString() {
        return "DiscountCardId{" +
                "value=" + value +
                '}';
    }

    private void validate() {
        if (value == null) {
            throw new TypedIdException(this);
        }
    }
}
