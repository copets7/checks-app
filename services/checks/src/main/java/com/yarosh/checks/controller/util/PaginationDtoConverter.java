package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.pagination.ContentPageRequestDto;
import com.yarosh.checks.controller.dto.pagination.ContentSortByDto;
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

    public ContentPageRequest convertToContentPageRequest(ContentPageRequestDto contentPageRequestDto) {
        return new ContentPageRequest(
                contentPageRequestDto.page(),
                contentPageRequestDto.size(),
                convertToConvertToSortBy(contentPageRequestDto.sortBy())
        );
    }

    private ContentSortBy convertToConvertToSortBy(ContentSortByDto sortByDto) {
        return new ContentSortBy(sortByDto.column(), sortByDto.isDesc());
    }

}
