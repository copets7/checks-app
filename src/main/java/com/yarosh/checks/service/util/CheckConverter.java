package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.repository.entity.ProductEntity;

public class CheckConverter implements BidirectionalConverter<Check, CheckEntity> {

    private final BidirectionalConverter<Product, ProductEntity> productConverter;

    public CheckConverter(BidirectionalConverter<Product, ProductEntity> productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public Check convertToDomain(CheckEntity entity) {
        return new Check(entity.getId(),
                         entity.getMarketName(),
                         entity.getCashierName(),
                         entity.getDate(),
                         entity.getTime(),
                         entity.getProducts().stream().map(productConverter::convertToDomain).toList(),
                         entity.getTotalPrice());
    }

    @Override
    public CheckEntity convertToEntity(Check check) {
        return new CheckEntity(check.getId(),
                               check.getMarketName(),
                               check.getCashierName(),
                               check.getDate(),
                               check.getTime(),
                               check.getProducts().stream().map(productConverter::convertToEntity).toList(),
                               check.getTotalPrice());
    }
}
