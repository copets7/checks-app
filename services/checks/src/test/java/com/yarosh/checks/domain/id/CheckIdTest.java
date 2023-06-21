package com.yarosh.checks.domain.id;

import com.yarosh.checks.domain.exception.TypedIdException;
import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class CheckIdTest {

    @Test
    public void createCheckId() {
        //given
        new CheckId(1L);
        //expected nothing
    }

    @Test
    public void createCheckIdAndThrowTypedIdException() {
        TypedIdException e = assertThrows(TypedIdException.class, () -> {
            new CheckId(null);
        });

        final String expectedMessage = "ID can not be null in";
        final String actualMessage = e.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}