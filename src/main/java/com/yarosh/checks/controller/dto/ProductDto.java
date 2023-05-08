package com.yarosh.checks.controller.dto;

public class ProductDto {

    private String description;
    private double price;
    private double discount;

    public ProductDto(String description, double price, double discount) {
        this.description = description;
        this.price = price;
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
