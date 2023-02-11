package com.yarosh.checks.domain;

import java.util.Objects;

public class DiscountCard implements Domain {

    private final Long id;
    private final double discount;

    public DiscountCard(Long id, double discount) {
        this.id = id;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return discount == that.discount && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, discount);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", amountOfDiscount=" + discount +
                '}';
    }
}
