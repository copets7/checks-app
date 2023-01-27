package com.yarosh.checks.service.good;

import com.yarosh.checks.domain.Product;
import com.yarosh.checks.service.CrudService;

import java.util.Optional;

public interface ProductService extends CrudService<Product, Long> {

    Optional<Product> getGoodForCheck(Long id, int quantity);
}
