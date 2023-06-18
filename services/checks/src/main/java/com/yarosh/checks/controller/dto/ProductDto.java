package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductDto(@JsonProperty("id") Long id,
                         @JsonProperty("description") String description,
                         @JsonProperty("price") Double price,
                         @JsonProperty("discount") Double discount) implements Dto {

    @JsonCreator
    public ProductDto {
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "value=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
