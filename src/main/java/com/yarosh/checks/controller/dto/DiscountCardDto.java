package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscountCardDto(Long id, double discount) implements Dto{

    @JsonCreator
    public DiscountCardDto(final @JsonProperty("id") Long id,
                           final @JsonProperty("discount") double discount) {
        this.id = id;
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "DiscountCardDto{" +
                "id=" + id +
                ", discount='" + discount + '\'' +
                '}';
    }
}
