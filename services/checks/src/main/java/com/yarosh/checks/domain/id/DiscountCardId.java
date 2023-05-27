package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record DiscountCardId(Long id) implements DomainId {

    public DiscountCardId(Long id) {
        this.id = id;
        validate();
    }

    @Override
    public String toString() {
        return "DiscountCardId{" +
                "id=" + id +
                '}';
    }

    private void validate() {
        if (id == null) {
            throw new TypedIdException(this);
        }
    }
}
