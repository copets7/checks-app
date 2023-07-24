package com.yarosh.checks.controller.dto.pagination;

public record ContentSortByDto(String column, boolean isDesc) {

    @Override
    public String toString() {
        return "ContentSortByDto{" +
                "column='" + column + '\'' +
                ", isDesc=" + isDesc +
                '}';
    }
}
