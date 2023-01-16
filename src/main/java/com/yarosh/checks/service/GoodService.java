package com.yarosh.checks.service;

import com.yarosh.checks.domain.Good;

import java.util.List;

public interface GoodService extends CrudService<Good, Long>{
    List<Good> getDiscountedGoods();
}
