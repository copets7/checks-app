package com.yarosh.library.repository.api.entity;

import com.yarosh.library.repository.api.ProductsColumnConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Entity
@Table(name = "checks")
public class CheckEntity implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "market_name")
    private String marketName;

    @Column(name = "cashier_name")
    private String cashierName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private LocalTime time;

    @Convert(converter = ProductsColumnConverter.class)
    private Map<ProductEntity, Integer> products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_card_id")
    private DiscountCardEntity discountCard;

    @Column(name = "total_price")
    private double totalPrice;

    public CheckEntity() {
    }

    public CheckEntity(final Long id,
                       final String marketName,
                       final String cashierName,
                       final LocalDate date,
                       final LocalTime time,
                       final Map<ProductEntity, Integer> products,
                       final DiscountCardEntity discountCard,
                       final double totalPrice) {
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

    public Map<ProductEntity, Integer> getProducts() {
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
