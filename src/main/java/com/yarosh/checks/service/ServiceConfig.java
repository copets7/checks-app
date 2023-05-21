package com.yarosh.checks.service;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;
import com.yarosh.checks.service.util.DiscountCardConverter;
import com.yarosh.checks.service.util.ProductConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public CrudService<Product, ProductId> productService(
           final CrudRepository<ProductEntity, Long> productRepository,
           final BidirectionalConverter<Product, ProductEntity> productConverter
    ) {
        return new ProductService(productRepository, productConverter);
    }

    @Bean
    public CrudService<DiscountCard, DiscountCardId> discountCardService(
           final CrudRepository<DiscountCardEntity, Long> discountCardRepository,
           final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter
    ) {
        return new DiscountCardService(discountCardRepository, discountCardConverter);
    }

    @Bean
    public BidirectionalConverter<Product, ProductEntity> productConverter() {
        return new ProductConverter();
    }

    @Bean
    public BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter() {
        return new DiscountCardConverter();
    }
}
