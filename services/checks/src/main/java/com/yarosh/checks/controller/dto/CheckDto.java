package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CheckDto(Long id,
                       LocalDate date,
                       LocalTime time,
                       Map<Long, Integer> products,
                       Long discountCardId) implements Dto {

    @JsonCreator
    public CheckDto(final @JsonProperty("id") Long id,
                    final @JsonProperty("date") LocalDate date,
                    final @JsonProperty("time") LocalTime time,
                    final @JsonProperty("products") Map<Long, Integer> products,
                    final @JsonProperty("discount_card_id") Long discountCardId) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.products = products;
        this.discountCardId = discountCardId;
    }

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
