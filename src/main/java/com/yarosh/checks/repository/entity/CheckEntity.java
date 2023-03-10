package com.yarosh.checks.repository.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CheckEntity implements Entity {

    private Long id;
    private String marketName;
    private String cashierName;
    private LocalDate date;
    private LocalTime time;
    private List<ProductEntity> products;
    private DiscountCardEntity discountCard;
    private double totalPrice;

    public CheckEntity() {
    }

    public CheckEntity(Long id,
                       String marketName,
                       String cashierName,
                       LocalDate date,
                       LocalTime time,
                       List<ProductEntity> products,
                       DiscountCardEntity discountCard,
                       double totalPrice) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.products = products;
        this.discountCard = discountCard;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public DiscountCardEntity getDiscountCard() {
        return discountCard;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "CheckEntity{" +
                "id=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discountCard=" + discountCard +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
