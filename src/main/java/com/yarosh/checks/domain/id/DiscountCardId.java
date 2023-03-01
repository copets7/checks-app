package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public class DiscountCardId implements DomainId {

    private final Long id;

    public DiscountCardId(Long id) {
        this.id = id;
        validate();
    }

    public Long getId() {
        return id;
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
