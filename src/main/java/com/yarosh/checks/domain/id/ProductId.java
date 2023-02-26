package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public class ProductId {

    private final Long id;

    public ProductId(Long id) {
        this.id = id;

        validate();
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ProductId{" +
                "id=" + id +
                '}';
    }

    private void validate() {
        if (id == null) {
            throw new TypedIdException("Id can't be null in {0}", this);
        }
    }
}
