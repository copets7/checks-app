package com.yarosh.checks.service.good;

import com.yarosh.checks.domain.Good;
import com.yarosh.checks.repository.GoodRepository;
import com.yarosh.checks.service.InvalidQuantityInCheckException;

import java.util.List;
import java.util.Optional;

public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;

    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Override
    public Optional<Good> getGoodForCheck(Long id, int quantity) {
        Optional<Good> good = get(id);
        if (good.isPresent()) {
            checkQuantityInShopHigherQuantityInCheck(good.get().getQuantityInShop(), quantity);
            //TODO: build hole logic
        }
        return Optional.empty();
    }

    @Override
    public Good add(Good domain) {
        return null;
    }

    @Override
    public Optional<Good> get(Long id) {
        return null;
    }

    @Override
    public List<Good> getAll() {
        return null;
    }

    @Override
    public Good update(Good domain) {
        return null;
    }

    @Override
    public void delete(Long aLong) {
    }

    private void checkQuantityInShopHigherQuantityInCheck(int quantityInShop, int quantityInCheck) {
        if (quantityInShop < quantityInCheck) {
            throw new InvalidQuantityInCheckException("Quantity in shop must be higher than quantity in check, in shop: {0}, in check: {1}",
                    quantityInShop, quantityInCheck);
        }
    }
}
