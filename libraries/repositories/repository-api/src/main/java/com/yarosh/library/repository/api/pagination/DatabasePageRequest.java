package com.yarosh.library.repository.api.pagination;

public record DatabasePageRequest(Integer pageNumber, Integer size, long offSet) {

}
