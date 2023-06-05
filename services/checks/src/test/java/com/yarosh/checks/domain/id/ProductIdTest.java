package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class ProductIdTest {

    @Test
    public void createProductId() {
        //given
        new ProductId(1L);
        //expected nothing
    }

    @Test
    public void createProductIdAndThrowTypedIdException() {
        TypedIdException e = assertThrows(TypedIdException.class, () -> {
            new ProductId(null);
        });

        final String expectedMessage = "ID can not be null in";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}