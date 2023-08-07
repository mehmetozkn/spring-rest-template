package com.resttemplate.REST.Template.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotFoundExceptionTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCustomErrorMessage() {
        String errorMessage = "Custom error message for User Not Found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);
        assertEquals(errorMessage, exception.getMessage());
    }
}