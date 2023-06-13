package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidCheckException;
import com.yarosh.checks.domain.id.CheckId;
import com.yarosh.checks.domain.id.DiscountCardId;
import com.yarosh.checks.domain.id.ProductId;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CheckTest {

    private final Map<Product, Integer> products = Map.of(
            new Product(Optional.of(new ProductId(1L)), "apples", 2.58, 10), 2,
            new Product(Optional.of(new ProductId(2L)), "milk", 3.58, 15), 3
    );

    private final Map<Product, Integer> emptyProductList = Map.of();

    private final LocalDate invalidLocalDate = LocalDate.now().plusDays(1L);
    private final LocalTime invalidLocalTime = LocalTime.now().plusHours(1L);

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
                    invalidLocalDate,
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
                    invalidLocalTime,
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
                    LocalTime.now(),
                    emptyProductList,
                    Optional.of(new DiscountCard(Optional.of(new DiscountCardId(1L)), 10))
            );
        });

        final String expectedMessage = "Product list can not be empty";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}