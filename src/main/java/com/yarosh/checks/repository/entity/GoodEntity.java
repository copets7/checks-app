package com.yarosh.checks.repository.entity;

public class GoodEntity {

    private Long id;
    private String description;
    private int quantityInShop;
    private int price;
    private double discount;

    public GoodEntity(Long id,
                      String description,
                      int quantityInShop,
                      int price,
                      double discount) {
        this.id = id;
        this.description = description;
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
    public String toString() {
        return "GoodEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantityInShop=" + quantityInShop +
                ", price=" + price +
                ", discount=" + discount +
                '}';
    }
}
