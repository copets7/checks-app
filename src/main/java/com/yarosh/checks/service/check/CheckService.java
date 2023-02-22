package com.yarosh.checks.service.check;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.service.CrudService;

import java.util.Map;

public interface CheckService extends CrudService<Check, Long> {

    Check performCheck(Long discountCardId, Map<Long, Integer> productsQuantity);
}
