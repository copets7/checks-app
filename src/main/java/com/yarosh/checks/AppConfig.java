package com.yarosh.checks;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.product.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.service.CrudService;
import com.yarosh.checks.service.DiscountCardService;
import com.yarosh.checks.service.ProductService;
import com.yarosh.checks.service.check.CheckServiceImpl;
import com.yarosh.checks.service.util.BidirectionalConverter;
import com.yarosh.checks.service.util.CheckConverter;
import com.yarosh.checks.service.util.DiscountCardConverter;
import com.yarosh.checks.service.util.ProductConverter;

public class AppConfig {

    public CrudService<Check, Long> checkService() {
        return new CheckServiceImpl(checkRepository(), productService(), discountCardService(), checkConverter());
    }

    public CrudService<Product, Long> productService() {
        return new ProductService(productRepository(), productConverter());
    }

    public CrudService<DiscountCard, Long> discountCardService() {
        return new DiscountCardService(discountCardRepository(), discountCardConverter());
    }

    public CrudRepository<ProductEntity, Long> productRepository() {
        return null;
    }

    public CrudRepository<CheckEntity, Long> checkRepository() {
        return null;
    }

    public CrudRepository<DiscountCardEntity, Long> discountCardRepository() {
        return null;
    }

    public BidirectionalConverter<Check, CheckEntity> checkConverter() {
        return new CheckConverter(productConverter());
    }

    public BidirectionalConverter<Product, ProductEntity> productConverter() {
        return new ProductConverter();
    }

    public BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter() {
        return new DiscountCardConverter();
    }

}
