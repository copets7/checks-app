package com.yarosh.checks.domain;

import java.util.Objects;

public class DiscountCard {

    private Long id;
    private int amountOfDiscount;

    public DiscountCard(Long id, int amountOfDiscount) {
        this.id = id;
        this.amountOfDiscount = amountOfDiscount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmountOfDiscount() {
        return amountOfDiscount;
    }

    public void setAmountOfDiscount(int amountOfDiscount) {
        this.amountOfDiscount = amountOfDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return amountOfDiscount == that.amountOfDiscount && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amountOfDiscount);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "id=" + id +
                ", amountOfDiscount=" + amountOfDiscount +
                '}';
    }
}
