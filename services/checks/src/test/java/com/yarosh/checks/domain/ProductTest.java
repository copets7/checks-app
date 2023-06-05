package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidProductException;
import com.yarosh.checks.domain.id.ProductId;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void createProduct() {
        //given
        new Product(Optional.of(new ProductId(1L)), "apples", Optional.empty(), 2.58, 10);
        //expected nothing
    }

    @Test
    public void createProductAndThrowInvalidProductException() {
        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            new Product(Optional.of(new ProductId(1L)), "", Optional.empty(), 2.58, 10);
        });

        final String expectedMessage = "Description is empty";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createProductWithInvalidPriceAndThrowInvalidProductException() {
        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            new Product(Optional.of(new ProductId(1L)), "Milk", Optional.empty(), 0, 10);
        });

        final String expectedMessage = "Price can not be equals to or lees than 0";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createProductWithInvalidDiscountAndThrowInvalidProductException() {
        InvalidProductException e = assertThrows(InvalidProductException.class, () -> {
            new Product(Optional.of(new ProductId(1L)), "Milk", Optional.empty(), 2.58, -1);
        });

        final String expectedMessage = "Discount can not be lees than 0";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}