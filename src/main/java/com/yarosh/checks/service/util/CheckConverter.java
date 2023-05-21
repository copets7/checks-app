package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.entity.ProductEntity;

import java.util.Optional;

public class CheckConverter implements BidirectionalConverter<Check, CheckEntity> {

    private final BidirectionalConverter<Product, ProductEntity> productConverter;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter;

    public CheckConverter(final BidirectionalConverter<Product, ProductEntity> productConverter,
                          final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter) {
        this.productConverter = productConverter;
        this.discountCardConverter = discountCardConverter;
    }

    @Override
    public Check convertToDomain(CheckEntity entity) {
        return new Check(
                Optional.of(new CheckId(entity.getId())),
                entity.getMarketName(),
                entity.getCashierName(),
                entity.getDate(),
                entity.getTime(),
                entity.getProducts().stream().map(productConverter::convertToDomain).toList(),
                Optional.ofNullable(discountCardConverter.convertToDomain(entity.getDiscountCard()))
        );
    }

    @Override
    public CheckEntity convertToEntity(Check check) {
        return new CheckEntity(
                check.getId().map(CheckId::id).orElse(null),
                check.getMarketName(),
                check.getCashierName(),
                check.getDate(),
                check.getTime(),
                check.getProducts().stream().map(productConverter::convertToEntity).toList(),
                check.getDiscountCard().map(discountCardConverter::convertToEntity).get(),
                check.getTotalPrice()
        );
    }
}
