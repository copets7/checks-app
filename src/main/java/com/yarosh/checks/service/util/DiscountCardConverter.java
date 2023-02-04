package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.repository.entity.DiscountCardEntity;

public class DiscountCardConverter implements BidirectionalConverter<DiscountCard, DiscountCardEntity> {

    @Override
    public DiscountCard convertToDomain(DiscountCardEntity entity) {
        return new DiscountCard(entity.getId(), entity.getDiscount());
    }

    @Override
    public DiscountCardEntity convertToEntity(DiscountCard card) {
        return new DiscountCardEntity(card.getId(), card.getDiscount());
    }
}
