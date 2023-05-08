package com.yarosh.checks.controller.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"description","price","discount"})
public class ProductView {

    private String description;
    private double price;
    private double discount;

    public ProductView(String description, double price, double discount) {
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
