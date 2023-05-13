package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDto(Long id, String description, double price, double discount) implements Dto {

    @JsonCreator
    public ProductDto(final @JsonProperty("id") Long id,
                      final @JsonProperty("description") String description,
                      final @JsonProperty("price") double price,
                      final @JsonProperty("discount") double discount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
