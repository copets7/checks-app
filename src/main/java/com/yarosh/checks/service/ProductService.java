package com.yarosh.checks.service;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ProductEntity addedProduct = productRepository.insert(productConverter.convertToEntity(product));
        return productConverter.convertToDomain(addedProduct);
    }

    @Override
    public Optional<Product> get(Long id) {
        Optional<ProductEntity> product = productRepository.find(id);
        if (product.isPresent()) {
            return Optional.of(productConverter.convertToDomain(product.get()));
        }

        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        List<ProductEntity> entities = productRepository.findAll();

        List<Product> products = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (ProductEntity entity : entities) {
                products.add(productConverter.convertToDomain(entity));
            }
        }

        return products;
    }

    @Override
    public Product update(Product product) {
        ProductEntity updatedProduct = productRepository.update(productConverter.convertToEntity(product));
        return productConverter.convertToDomain(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        productRepository.delete(id);
    }
}
