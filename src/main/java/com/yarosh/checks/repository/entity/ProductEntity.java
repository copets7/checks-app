package com.yarosh.checks.repository.entity;

public class ProductEntity implements Entity {

    private Long id;
    private String description;
    private double price;
    private double discount;

    public ProductEntity() { }

    public ProductEntity(Long id,
                         String description,
                         double price,
                         double discount) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "GoodEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
