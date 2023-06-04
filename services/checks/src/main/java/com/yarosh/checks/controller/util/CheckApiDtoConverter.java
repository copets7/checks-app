package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.CheckDto;
import com.yarosh.checks.controller.view.CheckView;
import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.id.CheckId;

import java.util.Optional;

public class CheckApiDtoConverter implements ApiDtoConverter<CheckDto, CheckView, Check> {

    @Override
    public CheckView convertDomainToView(Check domain) {
        return new CheckView(
                domain.getId().orElseThrow().id(),
                domain.getMarketName(),
                domain.getCashierName(),
                domain.getDate(),
                domain.getTime(),
                domain.getProducts(),
                domain.getDiscountCard().orElseThrow());
    }

    @Override
    public Check convertDtoToDomain(CheckDto dto) {
        return new Check(
                Optional.ofNullable(dto.id()).map(CheckId::new),
                dto.marketName(),
                dto.cashierName(),
                dto.date(),
                dto.time(),
                dto.products(),
                dto.discountCard());
    }
}
