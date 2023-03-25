package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.repository.entity.DiscountCardEntity;

import java.util.Optional;

public class DiscountCardConverter implements BidirectionalConverter<DiscountCard, DiscountCardEntity> {

    @Override
    public DiscountCard convertToDomain(DiscountCardEntity entity) {
        return new DiscountCard(Optional.of(new DiscountCardId(entity.getId())) , entity.getDiscount());
    }

    @Override
    public DiscountCardEntity convertToEntity(DiscountCard card) {
        return new DiscountCardEntity(card.getId().map(DiscountCardId::getId).orElse(null) , card.getDiscount());
    }
}
