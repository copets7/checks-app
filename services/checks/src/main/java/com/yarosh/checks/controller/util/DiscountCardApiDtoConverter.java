package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;

import java.util.Optional;

public class DiscountCardApiDtoConverter implements ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> {

    @Override
    public DiscountCardView convertDomainToView(DiscountCard domain) {
        return new DiscountCardView(
                domain.id().orElseThrow().value(),
                domain.discount()
        );
    }

    @Override
    public DiscountCard convertDtoToDomain(DiscountCardDto dto) {
        return new DiscountCard(
                Optional.ofNullable(dto.id()).map(DiscountCardId::new),
                dto.discount());
    }
}
