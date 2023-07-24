package com.yarosh.library.repository.api.pagination;

import com.yarosh.library.repository.api.entity.BaseEntity;

import java.util.List;

public record RepositoryPage<E extends BaseEntity>(List<E> rows, int size) {

    @Override
    public String toString() {
        return "DatabasePage{" +
                "rows=" + rows +
                ", size=" + size +
                '}';
    }
}
