package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidCashierException;
import com.yarosh.checks.domain.id.CashierId;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Optional;

public class Cashier {

    private final Optional<CashierId> id;
    private final String name;

    public Cashier(Optional<CashierId> id, String name) {
        this.id = id;
        this.name = name;
        validate();
    }

    public Optional<CashierId> getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cashier cashier = (Cashier) o;
        return Objects.equals(id, cashier.id) && Objects.equals(name, cashier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    private void validate() {
        if (StringUtils.isBlank(name)) {
            throw new InvalidCashierException("Name can't be null or empty in: {0}", this);
        }
    }
}
