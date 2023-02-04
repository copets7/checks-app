package com.yarosh.checks.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Check implements Domain {

    private Long id;
    private String marketName;
    private String cashierName;
    private LocalDate date;
    private LocalTime time;
    private List<Product> products;
    private int totalPrice;

    public Check(Long id,
                 String marketName,
                 String cashierName,
                 LocalDate date,
                 LocalTime time,
                 List<Product> products,
                 int totalPrice) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.products = products;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return totalPrice == check.totalPrice &&
                Objects.equals(id, check.id) &&
                Objects.equals(marketName, check.marketName) &&
                Objects.equals(cashierName, check.cashierName) &&
                Objects.equals(date, check.date) &&
                Objects.equals(time, check.time) &&
                Objects.equals(products, check.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marketName, cashierName, date, time, products, totalPrice);
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", goodList=" + products +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
