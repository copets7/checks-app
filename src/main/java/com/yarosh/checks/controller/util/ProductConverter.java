package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;

import java.util.Optional;

public class ProductConverter implements Converter<ProductDto, ProductView, Product> {

    @Override
    public ProductView domainConvertToView(Product domain) {
        return new ProductView(
                domain.getDescription(),
                domain.getPrice(),
                domain.getDiscount()
        );
    }

    @Override
    public ProductDto viewConvertToDto(ProductView view) {
        return new ProductDto(
                view.getDescription(),
                view.getPrice(),
                view.getDiscount()
        );
    }

    @Override
    public ProductView dtoConvertToView(ProductDto dto) {
        return new ProductView(
                dto.getDescription(),
                dto.getPrice(),
                dto.getDiscount()
        );
    }

    @Override
    public Product viewConvertToDomain(ProductView view) {
        return new Product(
                Optional.empty(),
                view.getDescription(),
                Optional.empty(),
                view.getPrice(),
                view.getDiscount()
        );
    }

    @Override
    public Product dtoConvertToDomain(ProductDto dto) {
        return new Product(
                Optional.empty(),
                dto.getDescription(),
                Optional.empty(),
                dto.getPrice(),
                dto.getDiscount()
        );
    }
}
