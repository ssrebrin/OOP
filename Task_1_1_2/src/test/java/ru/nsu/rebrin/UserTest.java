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

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{}));
        deckTest.add(new Card("Two", 2, "Diamonds", new int[]{}));

        user.take_card(deckTest, new int[]{0});
        user.take_card(deckTest, new int[]{0});

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(false, 0);

        String expectedOutput = "Your hand: [Two Diamonds (2), Ace Spades (11)]";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test2() {
        User user = new User();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{0}));
        deckTest.add(new Card("Two", 2, "Diamonds", new int[]{0}));

        user.take_card(deckTest, new int[]{0});
        user.take_card(deckTest, new int[]{0});

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(true, 0);

        String expectedOutput = "Your hand: [Two Diamonds (2), Ace Spades (11)] => 13";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test3() {
        User user = new User();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{0}));
        deckTest.add(new Card("Ace", 11, "Diamonds", new int[]{0}));

        user.take_card(deckTest, new int[]{0});
        user.take_card(deckTest, new int[]{0});
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        user.show_hand(true, 0);

        String expectedOutput = "Your hand: [Ace Diamonds (1), Ace Spades (1)] => 2";
        assertEquals(expectedOutput, outContent.toString().trim());
    }
}