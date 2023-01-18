package com.yarosh.checks.repository.entity;

import com.yarosh.checks.domain.DiscountCard;

public class CustomerEntity {

    private Long id;
    private String name;
    private DiscountCardEntity discountCard;

    public CustomerEntity(Long id, String name, DiscountCardEntity discountCard) {
        this.id = id;
        this.name = name;
        this.discountCard = discountCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DiscountCardEntity getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCardEntity discountCard) {
        this.discountCard = discountCard;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discountCard=" + discountCard +
                '}';
    }
}
