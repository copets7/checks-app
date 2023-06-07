package com.yarosh.checks.service.util;

import com.yarosh.checks.controller.dto.Dto;
import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.library.repository.api.entity.ProductEntity;

import java.util.Optional;


public class ProductConverter implements BidirectionalConverter<Product, ProductEntity, ProductView, ProductDto> {

    @Override
    public Product convertToDomain(ProductEntity entity) {
        return new Product(
                Optional.of(new ProductId(entity.getId())),
                entity.getDescription(),
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

    @Override
    public ProductView convertToView(Product product) {
        return new ProductView(
                product.id().map(ProductId::id).orElse(null),
                product.description(),
                product.price(),
                product.discount()
        );
    }

    @Override
    public Product convertDtoToDomain(ProductDto dto) {
        return new Product(
                Optional.of(new ProductId(dto.id())),
                dto.description(),
                dto.price(),
                dto.discount()
        );
    }
}
