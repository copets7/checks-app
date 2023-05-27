package com.yarosh.checks.service;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductService implements CrudService<Product, ProductId> {

    private final CrudRepository<ProductEntity, Long> productRepository;
    private final BidirectionalConverter<Product, ProductEntity> productConverter;

    public ProductService(final CrudRepository<ProductEntity, Long> productRepository,
                          final BidirectionalConverter<Product, ProductEntity> productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    public Product add(Product product) {
        return upsert(productRepository::insert, product);
    }

    @Override
    public Optional<Product> get(ProductId id) {
        return productRepository.select(id.id())
                .map(productConverter::convertToDomain);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.selectAll()
                .stream()
                .map(productConverter::convertToDomain)
                .toList();
    }

    @Override
    public Product update(Product product) {
        return upsert(productRepository::update, product);
    }

    @Override
    public void delete(ProductId id) {
        productRepository.delete(id.id());
    }

    private Product upsert(Function<ProductEntity, ProductEntity> upsert, Product product) {
        final ProductEntity upsertedProduct = upsert.apply(productConverter.convertToEntity(product));
        return productConverter.convertToDomain(upsertedProduct);
    }
}
