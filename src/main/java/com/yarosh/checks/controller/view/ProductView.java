package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductView implements View{

    private final String description;
    private final double price;
    private final double discount;

    public ProductView(String description, double price, double discount) {
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("price")
    public double getPrice() {
        return price;
    }

    @JsonProperty("discount")
    public double getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "ProductView{" +
                "description='" + description + '\'' +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
