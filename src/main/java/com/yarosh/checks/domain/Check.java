package com.yarosh.checks.domain;

import com.yarosh.checks.domain.product.Product;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Check implements Domain {

    private final Long id;
    private final String marketName;
    private final String cashierName;
    private final LocalDate date;
    private final LocalTime time;
    private final List<Product> products;
    private final Optional<DiscountCard> discountCard;
    private final int totalPrice;

    public Check(Long id,
                 String marketName,
                 String cashierName,
                 LocalDate date,
                 LocalTime time,
                 List<Product> products,
                 Optional<DiscountCard> discountCard,
                 int totalPrice) {
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

    public List<Product> getProducts() {
        return products;
    }

    public Optional<DiscountCard> getDiscountCard() {
        return discountCard;
    }

    public int getTotalPrice() {
        return totalPrice;
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
                Objects.equals(products, check.products) &&
                Objects.equals(discountCard, check.discountCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marketName, cashierName, date, time, products, discountCard, totalPrice);
    }

    @Override
    public String toString() {
        return "Check{" +
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
