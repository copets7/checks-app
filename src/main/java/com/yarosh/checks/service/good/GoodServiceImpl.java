package com.yarosh.checks.service.good;

import com.yarosh.checks.domain.Good;
import com.yarosh.checks.repository.GoodRepository;

import java.util.List;

public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;

    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @Override
    public Good add(Good domain) {
        return null;
    }

    @Override
    public Good get(Good domain) {
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
}
