package com.yarosh.checks.domain;

import java.util.Objects;
import java.util.Optional;

public class Product {

    private Long id;
    private String description;
    private Optional<Integer> quantityInCheck;
    private int price;
    private double discount;

    public Product(Long id,
                   String description,
                   Optional<Integer> quantityInCheck,
                   int price,
                   double discount) {
        this.id = id;
        this.description = description;
        this.quantityInCheck = quantityInCheck;
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

    public Optional<Integer> getQuantityInCheck() {
        return quantityInCheck;
    }

    public void setQuantityInCheck(Optional<Integer> quantityInCheck) {
        this.quantityInCheck = quantityInCheck;
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
        Product product = (Product) o;
        return price == product.price &&
                Double.compare(product.discount, discount) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(description, product.description) &&
                Objects.equals(quantityInCheck, product.quantityInCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantityInCheck, price, discount);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantityInCheck=" + quantityInCheck +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
