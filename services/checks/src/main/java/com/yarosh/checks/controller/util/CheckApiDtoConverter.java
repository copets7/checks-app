package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.CheckDto;
import com.yarosh.checks.controller.dto.DiscountCardDto;
import com.yarosh.checks.controller.dto.ProductDto;
import com.yarosh.checks.controller.view.CheckView;
import com.yarosh.checks.controller.view.DiscountCardView;
import com.yarosh.checks.controller.view.ProductView;
import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckApiDtoConverter implements ApiDtoConverter<CheckDto, CheckView, Check> {

    private final ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter;
    private final ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter;

    public CheckApiDtoConverter(ApiDtoConverter<ProductDto, ProductView, Product> productApiDtoConverter,
                                ApiDtoConverter<DiscountCardDto, DiscountCardView, DiscountCard> discountCardApiDtoConverter) {
        this.productApiDtoConverter = productApiDtoConverter;
        this.discountCardApiDtoConverter = discountCardApiDtoConverter;
    }

    @Override
    public CheckView convertDomainToView(Check check) {
        return new CheckView(
                check.getId().orElseThrow().id(),
                check.getMarketName(),
                check.getCashierName(),
                check.getDate(),
                check.getTime(),
                convertToProductsView(check.getProducts()),
                check.getDiscountCard().map(discountCardApiDtoConverter::convertDomainToView).orElseThrow()
        );
    }

    @Override
    public Check convertDtoToDomain(CheckDto dto) {
        return new Check(
                Optional.ofNullable(dto.id()).map(CheckId::new),
                dto.marketName(),
                dto.cashierName(),
                dto.date(),
                dto.time(),
                convertDtoToProduct(dto.products()),
                Optional.ofNullable(discountCardApiDtoConverter.convertDtoToDomain(dto.discountCard()))
        );
    }

    private Map<ProductView, Integer> convertToProductsView(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productApiDtoConverter.convertDomainToView(entry.getKey()), Map.Entry::getValue));
    }

    private Map<Product, Integer> convertDtoToProduct(Map<ProductDto, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productApiDtoConverter.convertDtoToDomain(entry.getKey()), Map.Entry::getValue));
    }
}
