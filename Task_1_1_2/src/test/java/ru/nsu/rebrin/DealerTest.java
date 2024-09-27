package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DealerTest {

    @Test
    void test1() {
        Dealer dealer = new Dealer();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{}));
        deckTest.add(new Card("Two", 2, "Diamonds", new int[]{}));

        dealer.take_card(deckTest, new int[]{0, 1});
        dealer.take_card(deckTest, new int[]{1});

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        dealer.show_hand(false, 0);

        String expectedOutput = "Dealer's cards: [Two Diamonds (2), <Close card>]";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test2() {
        Dealer dealer = new Dealer();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{}));
        deckTest.add(new Card("Two", 2, "Diamonds", new int[]{}));

        dealer.take_card(deckTest, new int[]{1});
        dealer.take_card(deckTest, new int[]{0});

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        dealer.show_hand(false, 0);

        String expectedOutput = "Dealer's cards: [<Close card>, Ace Spades (11)]";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test3() {
        Dealer dealer = new Dealer();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{}));
        deckTest.add(new Card("Ace", 11, "Diamonds", new int[]{}));

        dealer.take_card(deckTest, new int[]{0, 1, 2});
        dealer.take_card(deckTest, new int[]{});
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        dealer.show_hand(false, 1);

        String expectedOutput = "Dealer's cards: [Ace Diamonds (11), <Close card>]";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

    @Test
    void test4() {
        Dealer dealer = new Dealer();
        List<Card> deckTest = new ArrayList<>();

        deckTest.add(new Card("Ace", 11, "Spades", new int[]{}));
        deckTest.add(new Card("Ace", 11, "Diamonds", new int[]{}));

        dealer.take_card(deckTest, new int[]{0});
        dealer.take_card(deckTest, new int[]{1});
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        dealer.show_hand(true, 0);

        String expectedOutput = "Dealer's cards: [Ace Diamonds (1), Ace Spades (1)] => 2";
        assertEquals(expectedOutput, outContent.toString().trim());
    }

}