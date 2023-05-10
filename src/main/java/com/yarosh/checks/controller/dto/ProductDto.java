package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductDto implements Dto{

    private final String description;
    private final double price;
    private final double discount;

    @JsonCreator
    public ProductDto(final @JsonProperty("description") String description,
                      final @JsonProperty("price") double price,
                      final @JsonProperty("discount") double discount) {
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
