package com.yarosh.checks.controller.util;

import com.yarosh.checks.controller.dto.CheckDto;
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

    private final BidirectionalConverter<Product, ProductEntity, ProductView> productConverter;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity, DiscountCardView> discountCardConverter;

    public CheckApiDtoConverter(final BidirectionalConverter<Product, ProductEntity, ProductView> productConverter,
                                final BidirectionalConverter<DiscountCard, DiscountCardEntity, DiscountCardView> discountCardConverter) {
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
                convertToProducts(domain.getProducts()),
                domain.getDiscountCard().orElseThrow());
    }

    @Override
    public Check convertDtoToDomain(CheckDto dto) {
        return new Check(
                Optional.ofNullable(dto.id()).map(CheckId::new),
                dto.marketName(),
                dto.cashierName(),
                dto.date(),
                dto.time(),
                dto.products(),
                dto.discountCard());
    }

    private Map<ProductView, Integer> convertToProducts(Map<Product, Integer> products) {
        return products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> productConverter.convertToView(entry.getKey()), Map.Entry::getValue));
    }
}
