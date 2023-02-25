package com.yarosh.checks.service.check;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.service.CrudService;
import com.yarosh.checks.service.ProductNotFoundException;
import com.yarosh.checks.service.util.BidirectionalConverter;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class CheckServiceImpl implements CheckService {

    private final CrudRepository<CheckEntity, Long> checkRepository;

    private final CrudService<Product, Long> productService;
    private final CrudService<DiscountCard, Long> discountCardService;

    private final BidirectionalConverter<Check, CheckEntity> checkConverter;

    public CheckServiceImpl(CrudRepository<CheckEntity, Long> checkRepository,
                            CrudService<Product, Long> productService,
                            CrudService<DiscountCard, Long> discountCardService,
                            BidirectionalConverter<Check, CheckEntity> checkConverter) {
        this.checkRepository = checkRepository;
        this.productService = productService;
        this.discountCardService = discountCardService;
        this.checkConverter = checkConverter;
    }

    @Override
    public Check performCheck(Long discountCardId, Map<Long, Integer> productsQuantity) {
        Optional<DiscountCard> maybeDiscountCard = discountCardService.get(discountCardId);

        return null;
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

    private List<Product> performProducts(Map<Long, Integer> productsQuantity) {
        return productsQuantity.entrySet()
                .stream()
                .map(entry -> performProduct(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Product performProduct(long id, int quantity) {
        return productService.get(id)
                .map(product -> product.performProductForCheck(quantity))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
