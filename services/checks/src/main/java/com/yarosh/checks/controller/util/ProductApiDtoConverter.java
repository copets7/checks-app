package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;

import java.util.Optional;

public class ProductApiDtoConverter implements ApiDtoConverter<ProductDto, ProductView, Product> {

    @Override
    public ProductView convertDomainToView(Product domain) {
        return new ProductView(
                domain.id().orElseThrow().id(),
                domain.description(),
                domain.price(),
                domain.discount()
        );
    }

    @Override
    public Product convertDtoToDomain(ProductDto dto) {
        return new Product(
                Optional.ofNullable(dto.id()).map(ProductId::new),
                dto.description(),
                Optional.empty(),
                dto.price(),
                dto.discount()
        );
    }
}
