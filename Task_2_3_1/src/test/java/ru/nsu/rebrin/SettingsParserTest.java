package ru.nsu.rebrin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsParserTest {

    @Test
    void testValidInteger() {
        assertEquals(42, SettingsParser.parseOrDefault("42", 0));
    }

    @Test
    void testInvalidIntegerReturnsDefault() {
        assertEquals(5, SettingsParser.parseOrDefault("abc", 5));
    }

    @Test
    void testEmptyStringReturnsDefault() {
        assertEquals(10, SettingsParser.parseOrDefault("", 10));
    }

    @Test
    void testNegativeNumber() {
        assertEquals(-7, SettingsParser.parseOrDefault("-7", 0));
    }

}
