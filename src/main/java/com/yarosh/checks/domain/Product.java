package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidQuantityException;

import java.util.Objects;
import java.util.Optional;

public class Product implements Domain {

    private static final int INVALID_QUANTITY = 0;

    private final Long id;
    private final String description;
    private final Optional<Integer> quantityInCheck;
    private final double price;
    private final double discount;

    public Product(Long id,
                   String description,
                   Optional<Integer> quantityInCheck,
                   double price,
                   double discount) {
        this.id = id;
        this.description = description;
        this.quantityInCheck = quantityInCheck;
        this.price = price;
        this.discount = discount;
    }

    public int getValidatedQuantity() {
        return getQuantityInCheck().filter(quantity -> quantity > INVALID_QUANTITY)
                .orElseThrow(() -> new InvalidQuantityException(getId(), getQuantityInCheck().get()));
    }

    public Product performProductForCheck(int quantityInCheck) {
        if (quantityInCheck <= INVALID_QUANTITY) {
            throw new InvalidQuantityException(id, quantityInCheck);
        }

        return new Product(id, description, Optional.of(quantityInCheck), price, discount);
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

    public double getPrice() {
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
