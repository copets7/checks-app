package com.yarosh.checks.domain.product;

import com.yarosh.checks.domain.Domain;

import java.util.Objects;
import java.util.Optional;

public class Product implements Domain {

    private final Long id;
    private final String description;
    private final Optional<Integer> quantityInCheck;
    private final int price;
    private final double discount;

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

    public String getDescription() {
        return description;
    }

    public Optional<Integer> getQuantityInCheck() {
        return quantityInCheck;
    }

    public Product performProductForCheck(int quantityInCheck) {
        if (quantityInCheck <= 0) {
            throw new InvalidQuantityException(id, quantityInCheck);
        }

        return new Product(id, description, Optional.of(quantityInCheck), price, discount);
    }

    public int getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
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
        return "Product{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantityInCheck=" + quantityInCheck +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
