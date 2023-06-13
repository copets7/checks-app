package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CheckDto(Long id,
                       String marketName,
                       String cashierName,
                       LocalDate date,
                       LocalTime time,
                       Map<ProductDto, Integer> products,
                       DiscountCardDto discountCard) implements Dto {

    @JsonCreator
    public CheckDto(final @JsonProperty("id") Long id,
                    final @JsonProperty("market_name") String marketName,
                    final @JsonProperty("cashier_name") String cashierName,
                    final @JsonProperty("date") LocalDate date,
                    final @JsonProperty("time") LocalTime time,
                    final @JsonProperty("products") Map<ProductDto, Integer> products,
                    final @JsonProperty("discount_card_id") DiscountCardDto discountCard) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.products = products;
        this.discountCard = discountCard;
    }

    @Override
    public String toString() {
        return "CheckDto{" +
                "id=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discountCard=" + discountCard +
                '}';
    }
}
