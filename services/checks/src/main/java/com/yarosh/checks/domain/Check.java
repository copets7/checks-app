package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidCheckException;
import com.yarosh.checks.domain.id.CheckId;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Check implements Domain {

    private static final int NO_DISCOUNT = 0;
    private static final int MAX_DISCOUNT = 1;
    private static final double INVALID_TOTAL_PRICE = 0.0;

    private final Optional<CheckId> id;
    private final String marketName;
    private final String cashierName;
    private final LocalDate date;
    private final LocalTime time;
    private final List<Product> products;
    private final Optional<DiscountCard> discountCard;

    private final double totalPrice = countTotalPrice();

    public Check(Optional<CheckId> id,
                 String marketName,
                 String cashierName,
                 LocalDate date,
                 LocalTime time,
                 List<Product> products,
                 Optional<DiscountCard> discountCard) {
        this.id = id;
        this.marketName = marketName;
        this.cashierName = cashierName;
        this.date = date;
        this.time = time;
        this.products = products;
        this.discountCard = discountCard;
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

    public List<Product> getProducts() {
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

    private double countTotalPrice() {
        return products.stream()
                .map(product -> (product.price() * (MAX_DISCOUNT - countDiscount(product))) * product.getValidatedQuantity())
                .reduce(Double::sum)
                .filter(this::isTotalPriceValid)
                .orElseThrow();
    }

    private double countDiscount(Product product) {
        if (product.discount() > NO_DISCOUNT) {
            return product.discount();
        }

        return (discountCard.isPresent()) ? discountCard.get().discount() : NO_DISCOUNT;
    }

    private boolean isTotalPriceValid(Double price) {
        if (price <= INVALID_TOTAL_PRICE) {
            throw new InvalidCheckException(price);
        }

        return true;
    }

    private void validate() {
        if (StringUtils.isBlank(marketName)) {
            throw new InvalidCheckException("Market name is empty, {0}", this);
        } else if (StringUtils.isBlank(cashierName)) {
            throw new InvalidCheckException("Cashier name is empty, {0}", this);
        } else if (!LocalDate.now().equals(date)) {
            throw new InvalidCheckException("Date is not correct, {0}", this);
        } else if (!LocalTime.now().equals(time)) {
            throw new InvalidCheckException("Time is not correct, {0}", this);
        } else if (products.isEmpty()) {
            throw new InvalidCheckException("Product list can't be empty, {0}", this);
        }else if (totalPrice <= INVALID_TOTAL_PRICE) {
            throw new InvalidCheckException("Total price can't be equals to or lees than 0, {0}", this);
        }
    }
}
