package com.yarosh.library.reports.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public record CheckRecord(String marketName,
                          String cashierName,
                          LocalDate date,
                          LocalTime time,
                          List<ProductInfo> products,
                          Optional<Double> discount,
                          double totalPrice) implements Record {

    @Override
    public String toString() {
        return "CheckRecord{" +
                "marketName='" + marketName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", products=" + products +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
