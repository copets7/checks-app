package com.yarosh.library.repository.spring.data.decorator;

import com.yarosh.library.repository.api.CrudRepository;
import com.yarosh.library.repository.api.entity.DiscountCardEntity;
import com.yarosh.library.repository.spring.data.DiscountCardSpringDataRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DiscountCardRepositoryDecorator implements CrudRepository<DiscountCardEntity, Long> {

    private final DiscountCardSpringDataRepository discountCardJpaRepository;

    @Inject
    public DiscountCardRepositoryDecorator(DiscountCardSpringDataRepository discountCardJpaRepository) {
        this.discountCardJpaRepository = discountCardJpaRepository;
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity discountCard) {
        return discountCardJpaRepository.save(discountCard);
    }

    @Override
    public Optional<DiscountCardEntity> select(Long id) {
        return discountCardJpaRepository.findById(id);
    }

    @Override
    public List<DiscountCardEntity> selectAll() {
        return discountCardJpaRepository.findAll();
    }

    @Override
    public DiscountCardEntity update(DiscountCardEntity discountCard) {
        return discountCardJpaRepository.save(discountCard);
    }

    @Override
    public void delete(Long id) {
        discountCardJpaRepository.deleteById(id);
    }
}
