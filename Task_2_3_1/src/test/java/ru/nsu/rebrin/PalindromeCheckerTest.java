package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeCheckerTest {

    @Test
    void testSimplePalindrome() {
        assertTrue(PalindromeChecker.isPalindrome("madam"));
    }

    @Test
    void testPalindromeWithSpacesAndCase() {
        assertTrue(PalindromeChecker.isPalindrome("A man a plan a canal Panama"));
    }

    @Test
    void testNotPalindrome() {
        assertFalse(PalindromeChecker.isPalindrome("hello"));
    }

    @Test
    void testEmptyString() {
        assertTrue(PalindromeChecker.isPalindrome(""));
    }

    @Test
    void testNullInput() {
        assertFalse(PalindromeChecker.isPalindrome(null));
    }

    @Test
    void testPalindromeWithSymbols() {
        assertTrue(PalindromeChecker.isPalindrome("Was it a car or a cat I saw?"));
    }
}
