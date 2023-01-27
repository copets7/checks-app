package com.yarosh.checks.service.util;

public interface BidirectionalConverter<D, E> {

    D convertToDomain(E entity);
    E convertToEntity(D domain);
}
