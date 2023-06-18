package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;

import java.util.Optional;

public class ProductApiDtoConverterImpl implements ProductApiDtoConverter {

    @Override
    public ProductView convertDomainToView(Product domain) {
        return convertProductToView(domain, null);
    }

    @Override
    public Product convertDtoToDomain(ProductDto dto) {
        return new Product(
                Optional.ofNullable(dto.id()).map(ProductId::new),
                dto.description(),
                dto.price(),
                dto.discount()
        );
    }

    @Override
    public ProductView convertProductToView(Product product, Integer quantityInCheck) {
        return new ProductView(
                product.id().orElseThrow().value(),
                product.description(),
                product.price(),
                product.discount(),
                quantityInCheck
        );
    }
}
