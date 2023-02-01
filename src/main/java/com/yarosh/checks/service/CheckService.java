package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        return upsert(checkRepository::insert, check);
    }

    @Override
    public Optional<Check> get(Long id) {
        return checkRepository.find(id)
                .map(checkConverter::convertToDomain);
    }

    @Override
    public List<Check> getAll() {
        return checkRepository.findAll()
                .stream()
                .map(checkConverter::convertToDomain)
                .toList();
    }

    @Override
    public Check update(Check check) {
        return upsert(checkRepository::update, check);
    }

    @Override
    public void delete(Long id) {
        checkRepository.delete(id);
    }

    private Check upsert(Function<CheckEntity, CheckEntity> upsert, Check check) {
        CheckEntity upsertedCheck = upsert.apply(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(upsertedCheck);
    }
}
