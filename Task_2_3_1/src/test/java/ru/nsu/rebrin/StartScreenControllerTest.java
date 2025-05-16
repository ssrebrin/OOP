package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StartScreenControllerTest {
    @Test
    void testSimplePalindrome() {
        assertTrue(StartScreenController.isPalindrome("madam"));
    }

    @Test
    void testPalindromeWithSpacesAndCase() {
        assertTrue(StartScreenController.isPalindrome("A man a plan a canal Panama"));
    }

    @Test
    void testNotPalindrome() {
        assertFalse(StartScreenController.isPalindrome("hello"));
    }

    @Test
    void testEmptyString() {
        assertTrue(StartScreenController.isPalindrome(""));
    }

    @Test
    void testNullInput() {
        assertFalse(StartScreenController.isPalindrome(null));
    }

    @Test
    void testPalindromeWithSymbols() {
        assertTrue(StartScreenController.isPalindrome("Was it a car or a cat I saw?"));
    }

    @Test
    void testValidInteger() {
        assertEquals(42, StartScreenController.parseOrDefault("42", 0));
    }

    @Test
    void testInvalidIntegerReturnsDefault() {
        assertEquals(5, StartScreenController.parseOrDefault("abc", 5));
    }

    @Test
    void testEmptyStringReturnsDefault() {
        assertEquals(10, StartScreenController.parseOrDefault("", 10));
    }

    @Test
    void testNegativeNumber() {
        assertEquals(-7, StartScreenController.parseOrDefault("-7", 0));
    }

}
