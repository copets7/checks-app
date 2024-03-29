package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.util.converter.BidirectionalConverter;
import com.yarosh.checks.service.util.converter.CheckConverter;
import com.yarosh.checks.service.util.converter.DiscountCardConverter;
import com.yarosh.checks.service.util.converter.PaginationConverter;
import com.yarosh.checks.service.util.converter.ProductConverter;
import com.yarosh.library.reports.api.CheckRecord;
import com.yarosh.library.reports.api.ReportService;
import com.yarosh.library.reports.local.LocalReportsConfig;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.CheckEntity;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.spring.data.SpringDataRepositoryConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
//@Import(JdbcConfig.class)
@Import({SpringDataRepositoryConfig.class, LocalReportsConfig.class})
@PropertySource("classpath:application.properties")
@EnableCaching
@EnableScheduling
public class ServiceConfig {

    @Bean
    public PaginationConverter paginationConverter() {
        return new PaginationConverter();
    }

    @Bean
    public CrudService<Check, CheckId> checkService(
            final ReportService<CheckRecord> checkReportsService,
            final CrudRepository<CheckEntity, Long> checkRepository,
            final BidirectionalConverter<Check, CheckEntity> checkConverter,
            final PaginationConverter paginationConverter
    ) {
        return new CheckService(checkReportsService, checkRepository, checkConverter, paginationConverter);
    }

    @Bean
    public CrudService<Product, ProductId> productService(
            final CrudRepository<ProductEntity, Long> productRepository,
            final BidirectionalConverter<Product, ProductEntity> productConverter,
            final PaginationConverter paginationConverter
    ) {
        return new ProductService(productRepository, productConverter, paginationConverter);
    }

    @Bean
    public CrudService<DiscountCard, DiscountCardId> discountCardService(
            final CrudRepository<DiscountCardEntity, Long> discountCardRepository,
            final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter,
            final PaginationConverter paginationConverter
    ) {
        return new DiscountCardService(discountCardRepository, discountCardConverter, paginationConverter);
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
