package com.yarosh.checks.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record DiscountCardDto(@JsonProperty("id") Long id,
                              @JsonProperty("discount") Double discount) implements Dto {

    @JsonCreator
    public DiscountCardDto {
    }

    @Override
    public String toString() {
        return "DiscountCardDto{" +
                "id=" + id +
                ", discount='" + discount + '\'' +
                '}';
    }
}
