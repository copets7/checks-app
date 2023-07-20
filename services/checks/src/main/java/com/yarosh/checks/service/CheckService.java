package com.yarosh.checks.service;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.util.converter.BidirectionalConverter;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.CheckEntity;
import org.springframework.cache.annotation.Cacheable;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CheckService implements CrudService<Check, CheckId> {

    private final CrudRepository<CheckEntity, Long> checkRepository;

    private final BidirectionalConverter<Check, CheckEntity> checkConverter;

    @Inject
    public CheckService(final CrudRepository<CheckEntity, Long> checkRepository,
                        final BidirectionalConverter<Check, CheckEntity> checkConverter) {
        this.checkRepository = checkRepository;
        this.checkConverter = checkConverter;
    }

    @Override
    public Check add(Check check) {
        return upsert(checkRepository::insert, check);
    }

    @Override
    public Check getOrThrow(CheckId id) {
        return get(id).orElseThrow(ObjectNotFoundException.supplier(id.value(), "Check"));
    }

    @Override
    @Cacheable("check-cache")
    public Optional<Check> get(CheckId id) {
        return checkRepository.select(id.value())
                .map(checkConverter::convertToDomain);
    }

    @Override
    @Cacheable("checks-cache")
    public List<Check> getAll() {
        return checkRepository.selectAll()
                .stream()
                .map(checkConverter::convertToDomain)
                .toList();
    }

    @Override
    public ContentPage<Check> findAllWithPagination(ContentPageRequest request) {
        return null;
    }

    @Override
    public Check update(Check check) {
        return upsert(checkRepository::update, check);
    }

    @Override
    public void delete(CheckId id) {
        checkRepository.delete(id.value());
    }

    private Check upsert(Function<CheckEntity, CheckEntity> upsert, Check check) {
        final CheckEntity upsertedCheck = upsert.apply(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(upsertedCheck);
    }
}
