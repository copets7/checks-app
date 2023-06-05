package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidCheckException;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CheckTest {

    private final List<Product> products = List.of(
            new Product(Optional.of(new ProductId(1L)), "apples", Optional.of(2), 2.58, 10),
            new Product(Optional.of(new ProductId(2L)), "milk", Optional.of(1), 3.58, 15)
    );

    private final List<Product> emptyProductList = new ArrayList<>();

    private final LocalDate localDate = LocalDate.now().plusDays(1L);
    private final LocalTime localTime = LocalTime.now().plusHours(1L);

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

    @Test
    public void createCheckWithEmptyMarketNameAndThrowInvalidProductException() {
        InvalidCheckException e = assertThrows(InvalidCheckException.class, () -> {
            new Check(
                    Optional.of(new CheckId(1L)),
                    "",
                    "Cashier",
                    LocalDate.now(),
                    LocalTime.now(),
                    products,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Market name is empty";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createCheckWithEmptyCashierNameAndThrowInvalidProductException() {
        InvalidCheckException e = assertThrows(InvalidCheckException.class, () -> {
            new Check(
                    Optional.of(new CheckId(1L)),
                    "Market",
                    "",
                    LocalDate.now(),
                    LocalTime.now(),
                    products,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Cashier name is empty";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createCheckWithInvalidLocalDateAndThrowInvalidProductException() {
        InvalidCheckException e = assertThrows(InvalidCheckException.class, () -> {
            new Check(
                    Optional.of(new CheckId(1L)),
                    "Market",
                    "Cashier",
                    localDate,
                    LocalTime.now(),
                    products,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Date is not correct";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createCheckWithInvalidLocalTimeAndThrowInvalidProductException() {
        InvalidCheckException e = assertThrows(InvalidCheckException.class, () -> {
            new Check(
                    Optional.of(new CheckId(1L)),
                    "Market",
                    "Cashier",
                    LocalDate.now(),
                    localTime,
                    products,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Time is not correct";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createCheckWithEmptyProductListAndThrowInvalidProductException() {
        InvalidCheckException e = assertThrows(InvalidCheckException.class, () -> {
            new Check(
                    Optional.of(new CheckId(1L)),
                    "Market",
                    "Cashier",
                    LocalDate.now(),
                    localTime,
                    emptyProductList,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Product list can not be empty";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}