package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.Dto;
import com.yarosh.checks.controller.view.View;
import com.yarosh.checks.domain.Domain;

public interface Converter<D extends Dto, V extends View, P extends Domain> {

    V domainConvertToView(P domain);
    D viewConvertToDto(V view);
    V dtoConvertToView(D dto);
    P viewConvertToDomain(V view);
    P dtoConvertToDomain(D dto);
}
