package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test.
 */
public class BlackJackTest {

    private BlackJack blackJack;

    @BeforeEach
    void setUp() {
        blackJack = new BlackJack();
    }

    @Test
    void testPrepareDeck() {
        List<Card> deck = blackJack.prepare_deck();
        assertNotNull(deck);
        assertEquals(52, deck.size());
        assertTrue(deck.contains(new Card("Ace", 11, "Spades")));
        assertTrue(deck.contains(new Card("King", 10, "Hearts")));
    }

    @Test
    void testScanValidInput() {
        Scanner scanner = new Scanner("1\n0\n");
        blackJack.in = scanner;

        assertTrue(blackJack.scan());
        assertFalse(blackJack.scan());
    }

    @Test
    void testScanInvalidInput() {
        Scanner scanner = new Scanner("3\nabc\n1\n");
        blackJack.in = scanner;

        assertTrue(blackJack.scan());
    }

    @Test
    public void test() {
        BlackJack main = new BlackJack();
        List<Card> deck = main.prepare_deck();

        List<Card> deckTest = new ArrayList<Card>(52);
        {
            deckTest.add(0, new Card("Two", 2, "Spades"));
            deckTest.add(1, new Card("Two", 2, "Hearts"));
            deckTest.add(2, new Card("Two", 2, "Clubs"));
            deckTest.add(3, new Card("Two", 2, "Diamonds"));

            deckTest.add(4, new Card("Three", 3, "Spades"));
            deckTest.add(5, new Card("Three", 3, "Hearts"));
            deckTest.add(6, new Card("Three", 3, "Clubs"));
            deckTest.add(7, new Card("Three", 3, "Diamonds"));

            deckTest.add(8, new Card("Four", 4, "Spades"));
            deckTest.add(9, new Card("Four", 4, "Hearts"));
            deckTest.add(10, new Card("Four", 4, "Clubs"));
            deckTest.add(11, new Card("Four", 4, "Diamonds"));

            deckTest.add(12, new Card("Five", 5, "Spades"));
            deckTest.add(13, new Card("Five", 5, "Hearts"));
            deckTest.add(14, new Card("Five", 5, "Clubs"));
            deckTest.add(15, new Card("Five", 5, "Diamonds"));

            deckTest.add(16, new Card("Six", 6, "Spades"));
            deckTest.add(17, new Card("Six", 6, "Hearts"));
            deckTest.add(18, new Card("Six", 6, "Clubs"));
            deckTest.add(19, new Card("Six", 6, "Diamonds"));

            deckTest.add(20, new Card("Seven", 7, "Spades"));
            deckTest.add(21, new Card("Seven", 7, "Hearts"));
            deckTest.add(22, new Card("Seven", 7, "Clubs"));
            deckTest.add(23, new Card("Seven", 7, "Diamonds"));

            deckTest.add(24, new Card("Eight", 8, "Spades"));
            deckTest.add(25, new Card("Eight", 8, "Hearts"));
            deckTest.add(26, new Card("Eight", 8, "Clubs"));
            deckTest.add(27, new Card("Eight", 8, "Diamonds"));

            deckTest.add(28, new Card("Nine", 9, "Spades"));
            deckTest.add(29, new Card("Nine", 9, "Hearts"));
            deckTest.add(30, new Card("Nine", 9, "Clubs"));
            deckTest.add(31, new Card("Nine", 9, "Diamonds"));

            deckTest.add(32, new Card("Ten", 10, "Spades"));
            deckTest.add(33, new Card("Ten", 10, "Hearts"));
            deckTest.add(34, new Card("Ten", 10, "Clubs"));
            deckTest.add(35, new Card("Ten", 10, "Diamonds"));

            deckTest.add(36, new Card("Jack", 10, "Spades"));
            deckTest.add(37, new Card("Jack", 10, "Hearts"));
            deckTest.add(38, new Card("Jack", 10, "Clubs"));
            deckTest.add(39, new Card("Jack", 10, "Diamonds"));

            deckTest.add(40, new Card("Queen", 10, "Spades"));
            deckTest.add(41, new Card("Queen", 10, "Hearts"));
            deckTest.add(42, new Card("Queen", 10, "Clubs"));
            deckTest.add(43, new Card("Queen", 10, "Diamonds"));

            deckTest.add(44, new Card("King", 10, "Spades"));
            deckTest.add(45, new Card("King", 10, "Hearts"));
            deckTest.add(46, new Card("King", 10, "Clubs"));
            deckTest.add(47, new Card("King", 10, "Diamonds"));

            deckTest.add(48, new Card("Ace", 11, "Spades"));
            deckTest.add(49, new Card("Ace", 11, "Hearts"));
            deckTest.add(50, new Card("Ace", 11, "Clubs"));
            deckTest.add(51, new Card("Ace", 11, "Diamonds"));
        }

        assertTrue(deck.containsAll(deckTest));

    }

}