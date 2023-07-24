package com.yarosh.checks.service;

import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;
import com.yarosh.checks.service.util.converter.BidirectionalConverter;
import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DiscountCardService implements CrudService<DiscountCard, DiscountCardId> {

    private final CrudRepository<DiscountCardEntity, Long> discountCardRepository;
    private final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter;

    @Inject
    public DiscountCardService(final CrudRepository<DiscountCardEntity, Long> discountCardRepository,
                               final BidirectionalConverter<DiscountCard, DiscountCardEntity> discountCardConverter) {
        this.discountCardRepository = discountCardRepository;
        this.discountCardConverter = discountCardConverter;
    }

    @Override
    public DiscountCard add(DiscountCard card) {
        return upsert(discountCardRepository::insert, card);
    }

    @Override
    public DiscountCard getOrThrow(DiscountCardId id) {
        return get(id).orElseThrow(ObjectNotFoundException.supplier(id.value(), "Discount card"));
    }

    @Override
    public Optional<DiscountCard> get(DiscountCardId id) {
        return discountCardRepository.select(id.value())
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
    public ContentPage<DiscountCard> getAll(ContentPageRequest request) {
        return null;
    }

    @Override
    public DiscountCard update(DiscountCard card) {
        return upsert(discountCardRepository::update, card);
    }

    @Override
    public void delete(DiscountCardId id) {
        discountCardRepository.delete(id.value());
    }

    private DiscountCard upsert(Function<DiscountCardEntity, DiscountCardEntity> upsert, DiscountCard discountCard) {
        final DiscountCardEntity upsertedDiscountCard = upsert.apply(discountCardConverter.convertToEntity(discountCard));
        return discountCardConverter.convertToDomain(upsertedDiscountCard);
    }
}
