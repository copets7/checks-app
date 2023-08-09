package com.yarosh.checks.domain.pagination;

public record ContentPageRequest(Integer pageNumber, Integer size, ContentSortBy sortBy) {

    @Override
    public String toString() {
        return "ContentPageRequest{" +
                "pageNumber=" + pageNumber +
                ", size=" + size +
                ", sortBy=" + sortBy +
                '}';
    }
}
