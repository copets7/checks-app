package com.yarosh.checks.domain;

import java.util.Objects;

public class Good {

    private Long id;
    private String description;
    private int quantity;
    private int price;
    private double discount;

    public Good(Long id,
                String description,
                int quantity,
                int price,
                double discount) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Good good = (Good) o;
        return quantity == good.quantity && price == good.price && Objects.equals(id, good.id) && Objects.equals(description, good.description) && Objects.equals(discount, good.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantity, price, discount);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
