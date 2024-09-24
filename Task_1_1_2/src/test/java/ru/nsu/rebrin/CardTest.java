package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CardTest {

    @Test
    void testConstructor() {
        Card card = new Card("Hearts", 11, "A");
        assertEquals(11, card.meaning);
        assertEquals("Hearts", card.suit);
        assertEquals("A", card.value);
        assertFalse(card.open);
    }

    @Test
    void testEquals() {
        Card card1 = new Card("Hearts", 11, "A");
        Card card2 = new Card("Hearts", 11, "A");
        Card card3 = new Card("Diamonds", 10, "10");

        assertTrue(card1.equals(card2));
        assertFalse(card1.equals(card3));
        assertFalse(card1.equals(null));
        assertFalse(card1.equals(new Object()));
    }

    @Test
    void testOpen() {
        Card card = new Card("Hearts", 11, "A");
        assertFalse(card.open);

        card.open();
        assertTrue(card.open);

        card.close();
        assertFalse(card.open);
    }

}