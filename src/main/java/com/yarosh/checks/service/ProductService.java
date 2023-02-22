package com.yarosh.checks.service;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductService implements CrudService<Product, Long> {

    private final CrudRepository<ProductEntity, Long> productRepository;
    private final BidirectionalConverter<Product, ProductEntity> productConverter;

    public ProductService(CrudRepository<ProductEntity, Long> productRepository,
                          BidirectionalConverter<Product, ProductEntity> productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    public Product add(Product product) {
        return upsert(productRepository::insert, product);
    }

    @Override
    public Optional<Product> get(Long id) {
        return productRepository.find(id)
                .map(productConverter::convertToDomain);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productConverter::convertToDomain)
                .toList();
    }

    @Override
    public Product update(Product product) {
        return upsert(productRepository::update, product);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }

    private Product upsert(Function<ProductEntity, ProductEntity> upsert, Product product) {
        ProductEntity upsertedProduct = upsert.apply(productConverter.convertToEntity(product));
        return productConverter.convertToDomain(upsertedProduct);
    }
}
