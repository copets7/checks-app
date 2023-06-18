package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record CheckView(Long id,
                        String marketName,
                        String cashierName,
                        LocalDate date,
                        LocalTime time,
                        List<ProductView> products,
                        DiscountCardView discountCard,
                        Double totalPrice) implements View {

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
    public List<ProductView> products() {
        return products;
    }

    @Override
    @JsonProperty("discount_card_id")
    public DiscountCardView discountCard() {
        return discountCard;
    }

    @Override
    @JsonProperty("total_price")
    public Double totalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "CheckView{" +
                "value=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discountCard=" + discountCard +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
