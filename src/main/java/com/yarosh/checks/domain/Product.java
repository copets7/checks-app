package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidProductException;
import com.yarosh.checks.domain.id.ProductId;

import java.util.Objects;
import java.util.Optional;

public record Product(
        Optional<ProductId> id,
        String description,
        Optional<Integer> quantityInCheck,
        double price,
        double discount) implements Domain {

    private static final int INVALID_QUANTITY = 0;

    public int getValidatedQuantity() {
        return quantityInCheck().filter(quantity -> quantity > INVALID_QUANTITY)
                .orElseThrow(() -> InvalidProductException.quantityCase(id.get().getId(), quantityInCheck().get()));
    }

    public Product performForCheck(int quantityInCheck) {
        if (quantityInCheck <= INVALID_QUANTITY) {
            throw InvalidProductException.quantityCase(id.get().getId(), quantityInCheck().get());
        }

        return new Product(id, description, Optional.of(quantityInCheck), price, discount);
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
