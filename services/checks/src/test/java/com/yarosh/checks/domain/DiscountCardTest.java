package com.yarosh.checks.domain;

import com.yarosh.checks.domain.exception.InvalidDiscountCardException;
import com.yarosh.checks.domain.id.DiscountCardId;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class DiscountCardTest {

    @Test
    public void createDiscountCard() {
        //given
        new DiscountCard(Optional.of(new DiscountCardId(1L)), 10);
        //expected nothing
    }

    @Test(expected = InvalidDiscountCardException.class)
    public void createDiscountCardWithInvalidDiscountAndThrowInvalidDiscountCardException() {
        //given
        new DiscountCard(Optional.of(new DiscountCardId(1L)), -1);
        //expected nothing
    }

    @Test
    public void createDiscountCardAndThrowInvalidDiscountCardException() {
        InvalidDiscountCardException e = assertThrows(InvalidDiscountCardException.class, () -> {
            new DiscountCard(Optional.of(new DiscountCardId(1L)), -1);
        });

        final String expectedMessage = "Discount is less than 0.1";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}