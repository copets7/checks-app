package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                convertToProducts(entity.getProducts()),
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
                convertToProductEntities(check.getProducts()),
                check.getDiscountCard().map(discountCardConverter::convertToEntity).orElseThrow(),
                check.getTotalPrice()
        );
    }

    private Map<Product, Integer> convertToProducts(Map<ProductEntity, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productConverter.convertToDomain(entry.getKey()), Map.Entry::getValue));
    }

    private Map<ProductEntity, Integer> convertToProductEntities(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productConverter.convertToEntity(entry.getKey()), Map.Entry::getValue));
    }
}
