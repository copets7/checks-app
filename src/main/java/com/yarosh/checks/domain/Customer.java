package com.yarosh.checks.domain;

import java.util.Objects;

public class Customer {

    private Long id;
    private String name;
    private DiscountCard discountCard;

    public Customer(Long id, String name, DiscountCard discountCard) {
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

    public DiscountCard getDiscountCard() {
        return discountCard;
    }

    public void setDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(discountCard, customer.discountCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, discountCard);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", personName='" + name + '\'' +
                ", card=" + discountCard +
                '}';
    }
}
