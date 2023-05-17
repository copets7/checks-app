package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record ProductId(Long id) implements DomainId {

    public ProductId(Long id) {
        this.id = id;
        validate();
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "id=" + id +
                '}';
    }

    private void validate() {
        if (id == null) {
            throw new TypedIdException(this);
        }
    }
}
