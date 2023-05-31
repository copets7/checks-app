package com.yarosh.checks.domain;

import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class CheckTest {

    private final List<Product> products = List.of(
            new Product(Optional.of(new ProductId(1L)), "apples", Optional.of(2), 2.58, 10),
            new Product(Optional.of(new ProductId(2L)), "milk", Optional.of(1), 3.58, 15)
    );

    @Test
    public void createCheck() {
        //given
        new Check(
                Optional.of(new CheckId(1L)),
                "Market",
                "Cashier",
                LocalDate.now(),
                LocalTime.now(),
                products,
                Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
        );
        //expected nothing
    }
}