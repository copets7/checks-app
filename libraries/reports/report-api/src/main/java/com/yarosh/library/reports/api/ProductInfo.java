package com.yarosh.library.reports.api;

import java.util.Optional;

public record ProductInfo(String name, Integer number, Double discount) {

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                ", number=" + number +
                ", discount=" + discount +
                '}';
    }
}
