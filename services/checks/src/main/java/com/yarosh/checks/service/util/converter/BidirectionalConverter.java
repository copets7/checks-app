package com.yarosh.checks.service.util.converter;

import com.yarosh.checks.domain.Domain;
import com.yarosh.library.repository.api.entity.BaseEntity;

public interface BidirectionalConverter<D extends Domain, E extends BaseEntity> {

    D convertToDomain(E entity);

    E convertToEntity(D domain);


}
