package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class DiscountCardIdTest {

    @Test
    public void createDiscountCardId() {
        //given
        new DiscountCardId(1L);
        //expected nothing
    }

    @Test
    public void createDiscountCardIdAndThrowTypedIdException() {
        TypedIdException e = assertThrows(TypedIdException.class, () -> {
            new DiscountCardId(null);
        });

        final String expectedMessage = "ID can not be null in";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}