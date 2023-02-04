package com.yarosh.checks.repository.entity;

public class DiscountCardEntity implements Entity {

    private Long id;
    private double discount;

    public DiscountCardEntity(Long id, double discount) {
        this.id = id;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "DiscountCardEntity{" +
                "id=" + id +
                ", discount=" + discount +
                '}';
    }
}
