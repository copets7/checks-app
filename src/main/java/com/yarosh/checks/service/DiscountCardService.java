package com.yarosh.checks.service;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.service.util.BidirectionalConverter;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DiscountCardService implements CrudService<DiscountCard, DiscountCardId> {

    private final CrudRepository<DiscountCardEntity, Long> discountCardRepository;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter;

    @Inject
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
    public Optional<DiscountCard> get(DiscountCardId id) {
        return discountCardRepository.select(id.id())
                .map(discountCardConverter::convertToDomain);
    }

    @Override
    public List<DiscountCard> getAll() {
        return discountCardRepository.selectAll()
                .stream()
                .map(discountCardConverter::convertToDomain)
                .toList();
    }

    @Override
    public DiscountCard update(DiscountCard card) {
        return upsert(discountCardRepository::update, card);
    }

    @Override
    public void delete(DiscountCardId id) {
        discountCardRepository.delete(id.id());
    }

    private DiscountCard upsert(Function<DiscountCardEntity, DiscountCardEntity> upsert, DiscountCard discountCard) {
        final DiscountCardEntity upsertedDiscountCard = upsert.apply(discountCardConverter.convertToEntity(discountCard));
        return discountCardConverter.convertToDomain(upsertedDiscountCard);
    }
}
