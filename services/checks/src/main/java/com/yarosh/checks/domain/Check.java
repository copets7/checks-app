package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidCheckException;
import com.yarosh.checks.domain.id.CheckId;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Check implements Domain {

    private static final double NO_DISCOUNT = 0;
    private static final double MAX_DISCOUNT = 1;
    private static final double INVALID_TOTAL_PRICE = 0.0;
    private static final int INVALID_QUANTITY = 0;

    private final Optional<CheckId> id;
    private final String marketName;
    private final String cashierName;
    private final LocalDate date;
    private final LocalTime time;
    private final Map<Product, Integer> products;
    private final Optional<DiscountCard> discountCard;
    private final double totalPrice;

    public Check(Optional<CheckId> id,
                 String marketName,
                 String cashierName,
                 LocalDate date,
                 LocalTime time,
                 Map<Product, Integer> products,
                 Optional<DiscountCard> discountCard) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.products = products;
        this.discountCard = discountCard;
        this.totalPrice = countTotalPrice();
        validate();
    }

    public Optional<CheckId> getId() {
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

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Optional<DiscountCard> getDiscountCard() {
        return discountCard;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Double.compare(check.totalPrice, totalPrice) == 0
                && Objects.equals(id, check.id)
                && Objects.equals(marketName, check.marketName)
                && Objects.equals(cashierName, check.cashierName)
                && Objects.equals(date, check.date)
                && Objects.equals(time, check.time)
                && Objects.equals(products, check.products)
                && Objects.equals(discountCard, check.discountCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, marketName, cashierName, date, time, products, discountCard, totalPrice);
    }

    @Override
    public String toString() {
        return "Check{" +
                "value=" + id +
                ", marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discountCard=" + discountCard +
                ", totalPrice=" + totalPrice +
                '}';
    }

    private double countTotalPrice() {
        return products.keySet().stream()
                .map(this::countPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double countPrice(Product product) {
        return product.price() * (MAX_DISCOUNT - countDiscount(product)) * getValidatedQuantity(products.get(product));
    }

    private double countDiscount(Product product) {
        return product.discount() > NO_DISCOUNT ? product.discount() : discountCard.map(DiscountCard::discount).orElse(NO_DISCOUNT);
    }

    private Integer getValidatedQuantity(Integer quantity) {
        if (quantity <= INVALID_QUANTITY) {
            throw new InvalidCheckException("Quantity of product is equals or less than {0}, quantity: {1}", INVALID_QUANTITY, quantity);
        }
        return quantity;
    }

    private void validate() {
        if (StringUtils.isBlank(marketName)) {
            throw new InvalidCheckException("Market name is empty, {0}", this);
        } else if (StringUtils.isBlank(cashierName)) {
            throw new InvalidCheckException("Cashier name is empty, {0}", this);
        } else if (LocalDate.now().isBefore(date)) {
            throw new InvalidCheckException("Date is not correct, {0}", this);
        } else if (LocalTime.now().isBefore(time) && id.isEmpty()) {
            throw new InvalidCheckException("Time is not correct, {0}", this);
        } else if (products.isEmpty()) {
            throw new InvalidCheckException("Product list can not be empty, {0}", this);
        } else if (totalPrice <= INVALID_TOTAL_PRICE) {
            throw new InvalidCheckException("Total price in check is less than {0} or null, price: {1}", INVALID_TOTAL_PRICE, totalPrice);
        }
    }
}
