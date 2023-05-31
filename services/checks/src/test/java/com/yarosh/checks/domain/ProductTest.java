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

    @Test(expected = InvalidProductException.class)
    public void createProductWithInvalidPriceAndThrowInvalidProductException() {
        //given
        new Product(Optional.of(new ProductId(1L)), "apples", Optional.empty(), 0, 10);
        //expected nothing
    }

    @Test(expected = InvalidProductException.class)
    public void createProductWithInvalidDiscountAndThrowInvalidProductException() {
        //given
        new Product(Optional.of(new ProductId(1L)), "apples", Optional.empty(), 3.56, -1);
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

}