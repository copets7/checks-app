package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidProductException;
import com.yarosh.checks.domain.id.ProductId;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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

}