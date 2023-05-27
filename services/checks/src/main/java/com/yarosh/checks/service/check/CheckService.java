package com.yarosh.checks.service.check;

import com.yarosh.checks.domain.Check;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import com.yarosh.checks.service.CrudService;

import java.util.Map;

public interface CheckService extends CrudService<Check, CheckId> {

    Check performCheck(DiscountCardId discountCardId, Map<ProductId, Integer> productsQuantity);
}
