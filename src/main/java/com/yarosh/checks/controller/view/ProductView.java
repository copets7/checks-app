package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductView(String description, double price, double discount) implements View {

    @Override
    @JsonProperty("description")
    public String description() {
        return description;
    }

    @Override
    @JsonProperty("price")
    public double price() {
        return price;
    }

    @Override
    @JsonProperty("discount")
    public double discount() {
        return discount;
    }

    @Override
    public String toString() {
        return "ProductView{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
