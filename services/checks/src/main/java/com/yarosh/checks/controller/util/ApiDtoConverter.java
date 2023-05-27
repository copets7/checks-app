package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.Dto;
import com.yarosh.checks.controller.view.View;
import com.yarosh.checks.domain.Domain;

public interface ApiDtoConverter<DTO extends Dto, VIEW extends View, D extends Domain> {

    VIEW convertDomainToView(D domain);

    D convertDtoToDomain(DTO dto);
}
