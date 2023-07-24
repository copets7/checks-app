package com.yarosh.checks.service.util.converter;

import com.yarosh.checks.domain.Domain;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.domain.pagination.ContentSortBy;
import com.yarosh.library.repository.api.entity.BaseEntity;
import com.yarosh.library.repository.api.pagination.RepositoryPage;
import com.yarosh.library.repository.api.pagination.RepositoryPageRequest;
import com.yarosh.library.repository.api.pagination.RepositorySortBy;

import java.util.List;
import java.util.function.Function;

public class PaginationConverter {

    public <D extends Domain, E extends BaseEntity> ContentPage<D> convertToContentPage(
            RepositoryPage<E> databasePage, Function<List<E>, List<D>> convertContent, int nextPage
    ) {
        return new ContentPage<>(convertContent.apply(databasePage.rows()), databasePage.size(), nextPage);
    }

    public RepositoryPageRequest convertToRepositoryPageRequest(ContentPageRequest contentPageRequest) {
        return new RepositoryPageRequest(
                contentPageRequest.pageNumber(),
                contentPageRequest.size(),
                convertToRepositorySortBy(contentPageRequest.sortBy())
        );
    }

    private RepositorySortBy convertToRepositorySortBy(ContentSortBy sortBy) {
        return new RepositorySortBy(sortBy.getColumn(), sortBy.isDesc());
    }

}
