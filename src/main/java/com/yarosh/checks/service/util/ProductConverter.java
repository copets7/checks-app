package com.yarosh.checks.service.util;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.repository.entity.ProductEntity;

import java.util.Optional;


public class ProductConverter implements BidirectionalConverter<Product, ProductEntity> {

    @Override
    public Product convertToDomain(ProductEntity entity) {
        return new Product(
                Optional.of(new ProductId(entity.getId())),
                entity.getDescription(),
                Optional.empty(),
                entity.getPrice(),
                entity.getDiscount()
        );
    }

    @Override
    public ProductEntity convertToEntity(Product product) {
        return new ProductEntity(
                product.id().map(ProductId::id).orElse(null),
                product.description(),
                product.price(),
                product.discount()
        );
    }
}
