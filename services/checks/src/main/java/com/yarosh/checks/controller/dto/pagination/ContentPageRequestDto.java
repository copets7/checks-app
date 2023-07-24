package com.yarosh.checks.controller.dto.pagination;

public record ContentPageRequestDto(Integer page, Integer size, ContentSortByDto sortBy) {

    @Override
    public String toString() {
        return "ContentPageRequestDto{" +
                "page=" + page +
                ", size=" + size +
                ", sortBy=" + sortBy +
                '}';
    }
}
