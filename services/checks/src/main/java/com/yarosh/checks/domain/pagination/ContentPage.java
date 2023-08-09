package com.yarosh.checks.domain.pagination;

import com.yarosh.checks.domain.Domain;

import java.util.List;

public record ContentPage<D extends Domain>(List<D> content, int size, int nextPage) {

    @Override
    public String toString() {
        return "ContentPage{" +
                "content=" + content +
                ", size=" + size +
                ", nextPage=" + nextPage +
                '}';
    }
}
