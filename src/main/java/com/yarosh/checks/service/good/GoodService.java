package com.yarosh.checks.service.good;

import com.yarosh.checks.domain.Good;
import com.yarosh.checks.service.CrudService;

import java.util.Optional;

public interface GoodService extends CrudService<Good, Long> {

    Optional<Good> getGoodForCheck(Long id, int quantity);
}
