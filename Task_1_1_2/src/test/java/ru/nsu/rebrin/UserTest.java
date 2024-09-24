package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void test1() {
        User user = new User();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades"));
        deckTest.add(new Card("Two", 2, "Diamonds"));

        user.take_card(deckTest, true);
        user.take_card(deckTest, true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(false);

        String expectedOutput = "Your hand: [Two Diamonds (2), Ace Spades (11)]";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test2() {
        User user = new User();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades"));
        deckTest.add(new Card("Two", 2, "Diamonds"));

        user.take_card(deckTest, true);
        user.take_card(deckTest, true);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(true);

        String expectedOutput = "Your hand: [Two Diamonds (2), Ace Spades (11)] => 13";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test3() {
        User user = new User();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades"));
        deckTest.add(new Card("Ace", 11, "Diamonds"));

        user.take_card(deckTest, true);
        user.take_card(deckTest, true);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(true);

        String expectedOutput = "Your hand: [Ace Diamonds (1), Ace Spades (1)] => 2";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}