package com.yarosh.library.repository.api.pagination;

public record RepositorySortBy(String column, boolean isDesc) {

    @Override
    public String toString() {
        return "RepositorySortBy{" +
                "column='" + column + '\'' +
                ", isDesc=" + isDesc +
                '}';
    }
}
