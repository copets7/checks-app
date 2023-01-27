package com.yarosh.checks.service.good;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getGoodForCheck(Long id, int quantity) {
        return Optional.empty();
    }

    @Override
    public Product add(Product domain) {
        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Product update(Product domain) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
    }

}
