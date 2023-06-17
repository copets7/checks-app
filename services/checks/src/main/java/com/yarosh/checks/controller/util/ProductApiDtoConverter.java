package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Product;

public interface ProductApiDtoConverter extends ApiDtoConverter<ProductDto, ProductView, Product> {

    ProductView convertProductToView(Product product, Integer quantityInCheck);
}
