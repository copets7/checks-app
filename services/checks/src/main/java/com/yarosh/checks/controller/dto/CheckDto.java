package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CheckDto(@JsonProperty("id") Long id,
                       @JsonProperty("date") LocalDate date,
                       @JsonProperty("time") LocalTime time,
                       @JsonProperty("products") List<ProductPairDto> products,
                       @JsonProperty("discount_card_id") Long discountCardId) implements Dto {

    @JsonCreator
    public CheckDto { }

    @Override
    public String toString() {
        return "CheckDto{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discountCard=" + discountCardId +
                '}';
    }
}
