package com.yarosh.checks.repository.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CheckEntity {


    private Long id;
    private String marketName;
    private String cashierName;
    private LocalDate date;
    private LocalTime time;
    private List<GoodEntity> goods;
    private CustomerEntity customer;
    private int totalPrice;

    public CheckEntity(Long id,
                       String marketName,
                       String cashierName,
                       LocalDate date,
                       LocalTime time,
                       List<GoodEntity> goods,
                       CustomerEntity customer,
                       int totalPrice) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.goods = goods;
        this.customer = customer;
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

    public List<GoodEntity> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodEntity> goods) {
        this.goods = goods;
    }

    public CustomerEntity getPerson() {
        return customer;
    }

    public void setPerson(CustomerEntity customer) {
        this.customer = customer;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CheckEntity{" +
                "id=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", goods=" + goods +
                ", customer=" + customer +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
