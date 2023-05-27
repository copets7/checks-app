package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscountCardView(Long id, double discount) implements View {

    @Override
    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @Override
    @JsonProperty("discount")
    public double discount() {
        return discount;
    }

    @Override
    public String toString() {
        return "DiscountCardView{" +
                "id=" + id +
                ", discount='" + discount + '\'' +
                '}';
    }
}
