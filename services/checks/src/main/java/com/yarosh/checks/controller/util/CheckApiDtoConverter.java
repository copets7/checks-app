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
import com.yarosh.checks.service.util.BidirectionalConverter;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CheckApiDtoConverter implements ApiDtoConverter<CheckDto, CheckView, Check> {

    private final BidirectionalConverter<Product, ProductEntity, ProductView, ProductDto> productConverter;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity, DiscountCardView, DiscountCardDto> discountCardConverter;

    public CheckApiDtoConverter(final BidirectionalConverter<Product, ProductEntity, ProductView, ProductDto> productConverter,
                                final BidirectionalConverter<DiscountCard, DiscountCardEntity, DiscountCardView, DiscountCardDto> discountCardConverter) {
        this.productConverter = productConverter;
        this.discountCardConverter = discountCardConverter;
    }

    @Override
    public CheckView convertDomainToView(Check domain) {
        return new CheckView(
                domain.getId().orElseThrow().id(),
                domain.getMarketName(),
                domain.getCashierName(),
                domain.getDate(),
                domain.getTime(),
                convertToProductsView(domain.getProducts()),
                domain.getDiscountCard().map(discountCardConverter::convertToView).orElseThrow());
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
                dto.discountCard().map(discountCardConverter::convertDtoToDomain));
    }

    private Map<ProductView, Integer> convertToProductsView(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productConverter.convertToView(entry.getKey()), Map.Entry::getValue));
    }

    private Map<Product, Integer> convertDtoToProduct(Map<ProductDto, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productConverter.convertDtoToDomain(entry.getKey()), Map.Entry::getValue));
    }
}
