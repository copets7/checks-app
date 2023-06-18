package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductPairDto(@JsonProperty("id") Long id,
                             @JsonProperty("quantity_in_check") Integer quantityInCheck) {

    @JsonCreator
    public ProductPairDto { }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public Integer quantityInCheck() {
        return quantityInCheck;
    }

    @Override
    public String toString() {
        return "ProductPairDto{" +
                "value=" + id +
                ", quantityInCheck=" + quantityInCheck +
                '}';
    }
}
