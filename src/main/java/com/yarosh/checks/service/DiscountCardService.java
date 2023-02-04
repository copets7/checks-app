package com.yarosh.checks.service;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DiscountCardService implements CrudService<DiscountCard, Long> {

    private final CrudRepository<DiscountCardEntity, Long> discountCardRepository;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter;

    public DiscountCardService(CrudRepository<DiscountCardEntity, Long> discountCardRepository,
                               BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter) {
        this.discountCardRepository = discountCardRepository;
        this.discountCardConverter = discountCardConverter;
    }

    @Override
    public DiscountCard add(DiscountCard card) {
        return upsert(discountCardRepository::insert, card);
    }

    @Override
    public Optional<DiscountCard> get(Long id) {
        return discountCardRepository.find(id)
                .map(discountCardConverter::convertToDomain);
    }

    @Override
    public List<DiscountCard> getAll() {
        return discountCardRepository.findAll()
                .stream()
                .map(discountCardConverter::convertToDomain)
                .toList();
    }

    @Override
    public DiscountCard update(DiscountCard card) {
        return upsert(discountCardRepository::update, card);
    }

    @Override
    public void delete(Long id) {
        discountCardRepository.delete(id);
    }

    private DiscountCard upsert(Function<DiscountCardEntity, DiscountCardEntity> upsert, DiscountCard discountCard) {
        DiscountCardEntity upsertedDiscountCard = upsert.apply(discountCardConverter.convertToEntity(discountCard));
        return discountCardConverter.convertToDomain(upsertedDiscountCard);
    }
}
