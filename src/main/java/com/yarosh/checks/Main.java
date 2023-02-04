package com.yarosh.checks;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
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

public class Main {

    public static void main(String[] args) {
        CrudRepository<ProductEntity, Long> productRepository = productRepository();
        CrudRepository<CheckEntity, Long> checkRepository = checkRepository();
        CrudRepository<DiscountCardEntity, Long> discountCardRepository = discountCardRepository();

        BidirectionalConverter<Product, ProductEntity> productConverter = productConverter();
        BidirectionalConverter<Check, CheckEntity> checkConverter = checkConverter(productConverter);
        BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter = discountCardConverter();

        CrudService<Product, Long> productService = productService(productRepository, productConverter);
        CrudService<DiscountCard, Long> discountCardService = discountCardService(discountCardRepository, discountCardConverter);
        CrudService<Check, Long> checkService = checkService(checkRepository, productService, checkConverter);
    }

    private static CrudRepository<ProductEntity, Long> productRepository() {
        return null;
    }

    private static CrudRepository<CheckEntity, Long> checkRepository() {
        return null;
    }

    private static CrudRepository<DiscountCardEntity, Long> discountCardRepository() {
        return null;
    }

    private static BidirectionalConverter<Product, ProductEntity> productConverter() {
        return new ProductConverter();
    }

    private static BidirectionalConverter<Check, CheckEntity> checkConverter(BidirectionalConverter<Product, ProductEntity> productConverter) {
        return new CheckConverter(productConverter);
    }

    private static BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter() {
        return new DiscountCardConverter();
    }

    private static CrudService<Product, Long> productService(CrudRepository<ProductEntity, Long> productRepository,
                                                             BidirectionalConverter<Product, ProductEntity> productConverter) {
        return new ProductService(productRepository, productConverter);
    }

    private static CrudService<DiscountCard, Long> discountCardService(CrudRepository<DiscountCardEntity, Long> discountCardRepository,
                                                                       BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter) {
        return new DiscountCardService(discountCardRepository, discountCardConverter);
    }

    private static CrudService<Check, Long> checkService(CrudRepository<CheckEntity, Long> checkRepository,
                                                         CrudService<Product, Long> productService,
                                                         BidirectionalConverter<Check, CheckEntity> checkConverter) {
        return new CheckServiceImpl(checkRepository, productService, checkConverter);
    }
}
