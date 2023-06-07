package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CheckView(Long id,
                        String marketName,
                        String cashierName,
                        LocalDate date,
                        LocalTime time,
                        Map<ProductView, Integer> products,
                        DiscountCardView discountCard) implements View {

    @Override
    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @Override
    @JsonProperty("market_name")
    public String marketName() {
        return marketName;
    }

    @Override
    @JsonProperty("cashier_name")
    public String cashierName() {
        return cashierName;
    }

    @Override
    @JsonProperty("date")
    public LocalDate date() {
        return date;
    }

    @Override
    @JsonProperty("time")
    public LocalTime time() {
        return time;
    }

    @Override
    @JsonProperty("products")
    public Map<ProductView, Integer> products() {
        return products;
    }

    @Override
    @JsonProperty("discount_card_id")
    public DiscountCardView discountCard() {
        return discountCard;
    }

    @Override
    public String toString() {
        return "CheckView{" +
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
