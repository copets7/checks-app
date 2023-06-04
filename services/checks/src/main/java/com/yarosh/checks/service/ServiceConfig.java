package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.check.CheckServiceImpl;
import com.yarosh.checks.service.util.BidirectionalConverter;
import com.yarosh.checks.service.util.CheckConverter;
import com.yarosh.checks.service.util.DiscountCardConverter;
import com.yarosh.checks.service.util.ProductConverter;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.jdbc.JdbcConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import(JdbcConfig.class)
@PropertySource("classpath:application.properties")
public class ServiceConfig {

    @Bean
    public CrudService<Check, CheckId> checkService(
            final CrudRepository<CheckEntity, Long> checkRepository,
            final CrudService<Product, ProductId> productService,
            final CrudService<DiscountCard, DiscountCardId> discountCardService,
            final BidirectionalConverter<Check, CheckEntity> checkConverter,
            @Value("${app.market.name}")
            final String marketName,
            @Value("${app.cashier.name}")
            final String cashierName
    ) {
        return new CheckServiceImpl(checkRepository, productService, discountCardService, checkConverter, marketName, cashierName);
    }

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
    public BidirectionalConverter<Check, CheckEntity> checkConverter() {
        return new CheckConverter(productConverter(), discountCardConverter());
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
