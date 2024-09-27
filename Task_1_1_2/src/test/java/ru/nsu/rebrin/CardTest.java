package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Testing Card.
 */
public class CardTest {

    @Test
    void testConstructor() {
        Card card = new Card("Hearts", 11, "A", new int[]{0});
        assertEquals(11, card.meaning);
        assertEquals("Hearts", card.suit);
        assertEquals("A", card.value);
        assertArrayEquals(card.openn, new int[]{0});
    }

    @Test
    void testEquals() {
        Card card1 = new Card("Hearts", 11, "A", new int[]{});
        Card card2 = new Card("Hearts", 11, "A", new int[]{});
        Card card3 = new Card("Diamonds", 10, "10", new int[]{0});

        assertEquals(card1, card2);
        assertNotEquals(card1, card3);
        assertNotEquals(null, card1);
        assertNotEquals(card1, new Object());
    }

    @Test
    void testOpen() {
        Card card = new Card("Hearts", 11, "A", new int[]{});
        assertArrayEquals(card.openn, new int[]{});

        card.open(new int[]{0, 1});
        assertArrayEquals(card.openn, new int[]{0, 1});
    }

}