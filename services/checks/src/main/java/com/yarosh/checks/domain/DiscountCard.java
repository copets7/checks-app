package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidDiscountCardException;
import com.yarosh.checks.domain.id.DiscountCardId;

import java.util.Objects;
import java.util.Optional;

public record DiscountCard(Optional<DiscountCardId> id, double discount) implements Domain {

    private static final int NO_DISCOUNT = 0;

    public DiscountCard(Optional<DiscountCardId> id, double discount) {
        this.id = id;
        this.discount = discount;
        validate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return discount == that.discount && Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "value=" + id +
                ", amountOfDiscount=" + discount +
                '}';
    }

    private void validate() {
        if (discount <= NO_DISCOUNT) {
            throw new InvalidDiscountCardException("Discount is less than 0.1, {0}", this);
        }
    }
}
