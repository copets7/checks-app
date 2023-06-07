package com.yarosh.checks.service.util;

import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;

import java.util.Optional;

public class DiscountCardConverter implements BidirectionalConverter<DiscountCard, DiscountCardEntity, DiscountCardView> {

    @Override
    public DiscountCard convertToDomain(DiscountCardEntity entity) {
        return new DiscountCard(Optional.of(new DiscountCardId(entity.getId())), entity.getDiscount());
    }

    @Override
    public DiscountCardEntity convertToEntity(DiscountCard card) {
        return new DiscountCardEntity(card.id().map(DiscountCardId::id).orElse(null), card.discount());
    }

    @Override
    public DiscountCardView convertToView(DiscountCard card) {
        return new DiscountCardView(card.id().map(DiscountCardId::id).orElse(null), card.discount());
    }
}
