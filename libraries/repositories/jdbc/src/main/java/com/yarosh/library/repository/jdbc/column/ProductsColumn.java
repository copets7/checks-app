package com.yarosh.library.repository.jdbc.column;

import com.yarosh.library.repository.api.entity.ProductEntity;

public record ProductsColumn(Long id,
                             String description,
                             double price,
                             double discount,
                             int quantity) {

    public ProductEntity convertToProductEntity() {
        return new ProductEntity(id, description, price, discount);
    }

    @Override
    public String toString() {
        return "ProductsColumn{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                ", quantity=" + quantity +
                '}';
    }
}
