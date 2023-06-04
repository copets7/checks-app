package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yarosh.checks.domain.DiscountCard;
import com.yarosh.checks.domain.Product;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public record CheckDto(Long id,
                       String marketName,
                       String cashierName,
                       LocalDate date,
                       LocalTime time,
                       List<Product> products,
                       Optional<DiscountCard> discountCard) implements Dto {

    @JsonCreator
    public CheckDto(final @JsonProperty("id") Long id,
                    final @JsonProperty("market_name") String marketName,
                    final @JsonProperty("cashier_name") String cashierName,
                    final @JsonProperty("date") LocalDate date,
                    final @JsonProperty("time") LocalTime time,
                    final @JsonProperty("products") List<Product> products,
                    final @JsonProperty("discount_card_id") Optional<DiscountCard> discountCard) {
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
