package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidProductException;
import com.yarosh.checks.domain.id.ProductId;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

public record Product(
        Optional<ProductId> id,
        String description,
        double price,
        double discount) implements Domain {

    private static final double INVALID_PRICE = 0;
    private static final double INVALID_DISCOUNT = 0;

    public Product(Optional<ProductId> id,
                   String description,
                   double price,
                   double discount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.discount = discount;
        validate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price &&
                Double.compare(product.discount, discount) == 0 &&
                Objects.equals(id, product.id) &&
                Objects.equals(description, product.description);
    }

    @Override
    public String toString() {
        return "Product{" +
                "value=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }

    private void validate() {
        if (StringUtils.isBlank(description)) {
            throw new InvalidProductException("Description is empty, {0}", this);
        } else if (price <= INVALID_PRICE) {
            throw new InvalidProductException("Price can not be equals to or lees than 0, {0}", this);
        } else if (discount < INVALID_DISCOUNT) {
            throw new InvalidProductException("Discount can not be lees than 0, {0}", this);
        }
    }
}
