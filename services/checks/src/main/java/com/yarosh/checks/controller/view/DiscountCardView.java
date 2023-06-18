package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscountCardView(Long id, Double discount) implements View {

    @Override
    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @Override
    @JsonProperty("discount")
    public Double discount() {
        return discount;
    }

    @Override
    public String toString() {
        return "DiscountCardView{" +
                "value=" + id +
                ", discount='" + discount + '\'' +
                '}';
    }
}
