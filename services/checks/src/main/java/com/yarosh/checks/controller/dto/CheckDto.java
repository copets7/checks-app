package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CheckDto(@JsonProperty("id") Long id,
                       @JsonProperty("products") List<ProductPairDto> products,
                       @JsonProperty("discount_card_id") Long discountCardId) implements Dto {

    @JsonCreator
    public CheckDto {
    }

    @Override
    public String toString() {
        return "CheckDto{" +
                "value=" + id +
                ", products=" + products +
                ", discountCard=" + discountCardId +
                '}';
    }
}
