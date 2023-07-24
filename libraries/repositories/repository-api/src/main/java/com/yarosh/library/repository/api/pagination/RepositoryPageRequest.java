package com.yarosh.library.repository.api.pagination;

public record RepositoryPageRequest(Integer pageNumber, Integer size, RepositorySortBy sortBy) {

    @Override
    public String toString() {
        return "RepositoryPageRequest{" +
                "pageNumber=" + pageNumber +
                ", size=" + size +
                ", sortBy=" + sortBy +
                '}';
    }
}
