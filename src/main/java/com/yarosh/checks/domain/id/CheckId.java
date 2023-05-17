package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;

public record CheckId(Long id) implements DomainId {

    public CheckId(Long id) {
        this.id = id;
        validate();
    }

    @Override
    public String toString() {
        return "CheckId{" +
                "id=" + id +
                '}';
    }

    private void validate() {
        if (id == null) {
            throw new TypedIdException(this);
        }
    }
}
