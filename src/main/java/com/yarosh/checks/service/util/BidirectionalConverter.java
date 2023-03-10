package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.Domain;
import com.yarosh.checks.repository.entity.Entity;

public interface BidirectionalConverter<D extends Domain, E extends Entity> {

    D convertToDomain(E entity);

    E convertToEntity(D domain);
}
