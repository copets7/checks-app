package com.yarosh.checks.service.check;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
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

    private final CrudService<Product, ProductId> productService;
    private final CrudService<DiscountCard, DiscountCardId> discountCardService;

    private final BidirectionalConverter<Check, CheckEntity> checkConverter;

    public CheckServiceImpl(CrudRepository<CheckEntity, Long> checkRepository,
                            CrudService<Product, ProductId> productService,
                            CrudService<DiscountCard, DiscountCardId> discountCardService,
                            BidirectionalConverter<Check, CheckEntity> checkConverter) {
        this.checkRepository = checkRepository;
        this.productService = productService;
        this.discountCardService = discountCardService;
        this.checkConverter = checkConverter;
    }

    @Override
    public Check performCheck(DiscountCardId discountCardId, Map<ProductId, Integer> productsQuantity) {
        Optional<DiscountCard> maybeDiscountCard = discountCardService.get(discountCardId);
        List<Product> products = performProducts(productsQuantity);
        return null;
    }

    @Override
    public Check add(Check check) {
        return upsert(checkRepository::insert, check);
    }

    @Override
    public Optional<Check> get(CheckId id) {
        return checkRepository.find(id.getId())
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
    public void delete(CheckId id) {
        checkRepository.delete(id.getId());
    }

    private Check upsert(Function<CheckEntity, CheckEntity> upsert, Check check) {
        CheckEntity upsertedCheck = upsert.apply(checkConverter.convertToEntity(check));
        return checkConverter.convertToDomain(upsertedCheck);
    }

    private List<Product> performProducts(Map<ProductId, Integer> productsQuantity) {
        return productsQuantity.entrySet()
                .stream()
                .map(entry -> performProduct(entry.getKey(), entry.getValue()))
                .toList();
    }

    private Product performProduct(ProductId id, int quantity) {
        return productService.get(id)
                .map(product -> product.performForCheck(quantity))
                .orElseThrow(() -> new ProductNotFoundException(id.getId()));
    }
}
