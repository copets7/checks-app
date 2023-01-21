package com.yarosh.checks.domain;

import java.util.Objects;
import java.util.Optional;

public class Good {

    private Long id;
    private String description;
    private Optional<Integer> quantityInCheck;
    private int quantityInShop;
    private int price;
    private double discount;

    public Good(Long id,
                String description,
                Optional<Integer> quantityInCheck,
                int quantityInShop,
                int price,
                double discount) {
        this.id = id;
        this.description = description;
        this.quantityInCheck = quantityInCheck;
        this.quantityInShop = quantityInShop;
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

    public int getQuantityInShop() {
        return quantityInShop;
    }

    public void setQuantityInShop(int quantityInShop) {
        this.quantityInShop = quantityInShop;
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
        Good good = (Good) o;
        return quantityInCheck == good.quantityInCheck &&
                quantityInShop == good.quantityInShop &&
                price == good.price &&
                Double.compare(good.discount, discount) == 0 &&
                Objects.equals(id, good.id) &&
                Objects.equals(description, good.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantityInCheck, quantityInShop, price, discount);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantityInCheck=" + quantityInCheck +
                ", quantityInShop=" + quantityInShop +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
