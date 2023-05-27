package com.yarosh.library.repository.api.entity;

public class DiscountCardEntity implements Entity {

    private Long id;
    private double discount;

    public DiscountCardEntity() {
    }

    public DiscountCardEntity(final Long id, final double discount) {
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
    public String toString() {
        return "DiscountCardEntity{" +
                "id=" + id +
                ", discount=" + discount +
                '}';
    }
}
