package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.repository.entity.ProductEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckService implements CrudService<Check, Long> {

    private final CrudRepository<CheckEntity, Long> checkRepository;
    private final CrudService<Product, Long> productService;
    private final BidirectionalConverter<Check, CheckEntity> checkConverter;

    public CheckService(CrudRepository<CheckEntity, Long> checkRepository,
                        CrudService<Product, Long> productService,
                        BidirectionalConverter<Check, CheckEntity> checkConverter) {
        this.checkRepository = checkRepository;
        this.productService = productService;
        this.checkConverter = checkConverter;
    }

    @Override
    public Check add(Check check) {
        CheckEntity checkEntity = checkRepository.insert(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(checkEntity);
    }

    @Override
    public Optional<Check> get(Long id) {
        Optional<CheckEntity> check = checkRepository.find(id);

        if (check.isPresent()) {
            return Optional.of(checkConverter.convertToDomain(check.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<Check> getAll() {
        List<CheckEntity> entities = checkRepository.findAll();

        List<Check> checks = new ArrayList<>();
        if (!entities.isEmpty()) {
            for (CheckEntity entity : entities) {
                checks.add(checkConverter.convertToDomain(entity));
            }
        }

        return checks;
    }

    @Override
    public Check update(Check check) {
        CheckEntity updatedCheck = checkRepository.update(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(updatedCheck);
    }

    @Override
    public void delete(Long id) {
        checkRepository.delete(id);
    }
}
