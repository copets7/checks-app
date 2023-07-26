package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.view.ContentPageView;
import com.yarosh.checks.controller.view.View;
import com.yarosh.checks.domain.Domain;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.domain.pagination.ContentSortBy;

import java.util.List;
import java.util.function.Function;

public class PaginationDtoConverter {

    public <V extends View, D extends Domain> ContentPageView<V> convertToContentPageView(
            ContentPage<D> contentPage, Function<List<D>, List<V>> convertContent
    ) {
        return new ContentPageView<>(convertContent.apply(contentPage.content()), contentPage.size(), contentPage.nextPage());
    }

    public ContentPageRequest convertToContentPageRequest(Integer page, Integer size, String column, Boolean isDesc) {
        return new ContentPageRequest(page, size, createContentSortBy(column, isDesc));
    }

    private ContentSortBy createContentSortBy(String column, Boolean isDesc) {
        if (column != null && isDesc != null) {
            return new ContentSortBy(column, isDesc);
        }
        if (column != null) {
            return new ContentSortBy(column);
        }
        if (isDesc != null) {
            return new ContentSortBy(isDesc);
        }
        return new ContentSortBy();
    }
}
