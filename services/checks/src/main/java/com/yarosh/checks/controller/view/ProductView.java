package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductView(Long id, String description, Double price, Double discount, Integer quantityInCheck) implements View {

    @Override
    @JsonProperty("id")
    public Long id() {
        return id;
    }

    @Override
    @JsonProperty("description")
    public String description() {
        return description;
    }

    @Override
    @JsonProperty("price")
    public Double price() {
        return price;
    }

    @Override
    @JsonProperty("discount")
    public Double discount() {
        return discount;
    }

    @Override
    @JsonProperty("quantity_in_check")
    public Integer quantityInCheck() {
        return quantityInCheck;
    }

    @Override
    public String toString() {
        return "ProductView{" +
                "value=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", quantityInCheck=" + quantityInCheck +
                '}';
    }
}
