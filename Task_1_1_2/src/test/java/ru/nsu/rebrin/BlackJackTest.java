package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Testing BlackJack.
 */
public class BlackJackTest {

    private BlackJack blackJack;
    private List<Card> testDeck;
    private ByteArrayOutputStream outContent;
    private final PrintStream originalOut = System.out;

    /**
     * setUp.
     */
    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        blackJack = new BlackJack();

        testDeck = new ArrayList<>(52);
        {
            testDeck.add(new Card("Two", 2, "Spades"));
            testDeck.add(new Card("Two", 2, "Hearts"));
            testDeck.add(new Card("Two", 2, "Clubs"));
            testDeck.add(new Card("Two", 2, "Diamonds"));
            testDeck.add(new Card("Three", 3, "Spades"));
            testDeck.add(new Card("Three", 3, "Hearts"));
            testDeck.add(new Card("Three", 3, "Clubs"));
            testDeck.add(new Card("Three", 3, "Diamonds"));
            testDeck.add(new Card("Four", 4, "Spades"));
            testDeck.add(new Card("Four", 4, "Hearts"));
            testDeck.add(new Card("Four", 4, "Clubs"));
            testDeck.add(new Card("Four", 4, "Diamonds"));
            testDeck.add(new Card("Five", 5, "Spades"));
            testDeck.add(new Card("Five", 5, "Hearts"));
            testDeck.add(new Card("Five", 5, "Clubs"));
            testDeck.add(new Card("Five", 5, "Diamonds"));
            testDeck.add(new Card("Six", 6, "Spades"));
            testDeck.add(new Card("Six", 6, "Hearts"));
            testDeck.add(new Card("Six", 6, "Clubs"));
            testDeck.add(new Card("Six", 6, "Diamonds"));
            testDeck.add(new Card("Seven", 7, "Spades"));
            testDeck.add(new Card("Seven", 7, "Hearts"));
            testDeck.add(new Card("Seven", 7, "Clubs"));
            testDeck.add(new Card("Seven", 7, "Diamonds"));
            testDeck.add(new Card("Eight", 8, "Spades"));
            testDeck.add(new Card("Eight", 8, "Hearts"));
            testDeck.add(new Card("Eight", 8, "Clubs"));
            testDeck.add(new Card("Eight", 8, "Diamonds"));
            testDeck.add(new Card("Nine", 9, "Spades"));
            testDeck.add(new Card("Nine", 9, "Hearts"));
            testDeck.add(new Card("Nine", 9, "Clubs"));
            testDeck.add(new Card("Nine", 9, "Diamonds"));
            testDeck.add(new Card("Ten", 10, "Spades"));
            testDeck.add(new Card("Ten", 10, "Hearts"));
            testDeck.add(new Card("Ten", 10, "Clubs"));
            testDeck.add(new Card("Ten", 10, "Diamonds"));
            testDeck.add(new Card("Jack", 10, "Spades"));
            testDeck.add(new Card("Jack", 10, "Hearts"));
            testDeck.add(new Card("Jack", 10, "Clubs"));
            testDeck.add(new Card("Jack", 10, "Diamonds"));
            testDeck.add(new Card("Queen", 10, "Spades"));
            testDeck.add(new Card("Queen", 10, "Hearts"));
            testDeck.add(new Card("Queen", 10, "Clubs"));
            testDeck.add(new Card("Queen", 10, "Diamonds"));
            testDeck.add(new Card("King", 10, "Spades"));
            testDeck.add(new Card("King", 10, "Hearts"));
            testDeck.add(new Card("King", 10, "Clubs"));
            testDeck.add(new Card("King", 10, "Diamonds"));
            testDeck.add(new Card("Ace", 11, "Spades"));
            testDeck.add(new Card("Ace", 11, "Hearts"));
            testDeck.add(new Card("Ace", 11, "Clubs"));
            testDeck.add(new Card("Ace", 11, "Diamonds"));
        }
    }

    @Test
    void testPrepareDeck() {
        List<Card> deck = blackJack.prepare_deck();
        assertNotNull(deck);
        assertEquals(52, deck.size());
        assertTrue(deck.containsAll(testDeck));
    }

    @Test
    void testShuffle() {
        List<Card> originalDeck = blackJack.prepare_deck();
        blackJack.shuffle(originalDeck);
        assertNotEquals(testDeck, originalDeck);
    }

    @Test
    void testScanValidInput() {
        blackJack.in = new Scanner("1\n0\n");

        assertTrue(blackJack.scan());
        assertFalse(blackJack.scan());
    }

    @Test
    void testScanInvalidInput() {
        blackJack.in = new Scanner("3\nabc\n1\n");

        assertTrue(blackJack.scan());
    }

    @Test
    void testUserPointsCalculation() {
        User user = new User();
        user.take_card(testDeck, true);
        user.take_card(testDeck, true);
        user.points();

        assertTrue(user.score > 0);
    }

    @Test
    void testDealerPointsCalculation() {
        Dealer dealer = new Dealer();
        dealer.take_card(testDeck, true);
        dealer.take_card(testDeck, true);
        dealer.points();

        assertTrue(dealer.score > 0);
    }

    @Test
    public void testGame() {

        blackJack.in = new Scanner("0\n0\n");
        InputStream originalIn = System.in;

        User user = new User();
        Dealer dealer = new Dealer();
        blackJack.playRound(testDeck, user, dealer);

        assertTrue(user.wins > 0 || dealer.wins > 0);

    }

    @Test
    public void testAfc() {

        blackJack.in = new Scanner("0\n1\n");
        InputStream originalIn = System.in;

        try {
            assertFalse(blackJack.askForContinue());
            assertTrue(blackJack.askForContinue());
        } finally {
            System.setIn(originalIn);
        }

    }

    @Test
    public void testDetWin() {

        User user = new User();
        Dealer dealer = new Dealer();

        user.score = 0;
        dealer.score = 0;

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        blackJack.determineWinner(user, dealer);

        String expectedOutput = "Game over. Dead heat.\n";
        String actualOutput = outContent.toString().replace("\r", "");

        assertEquals(expectedOutput.trim(), actualOutput.trim());

        user.score = 0;
        dealer.score = 1;

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        blackJack.determineWinner(user, dealer);

        expectedOutput = "Game over. You lose.\n";
        actualOutput = outContent.toString().replace("\r", "");

        assertEquals(expectedOutput.trim(), actualOutput.trim());

        user.score = 1;
        dealer.score = 0;

        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        blackJack.determineWinner(user, dealer);

        expectedOutput = "Game over. You win.\n";
        actualOutput = outContent.toString().replace("\r", "");

        assertEquals(expectedOutput.trim(), actualOutput.trim());

    }

    @Test
    public void testUsTurn() {

        User user = new User();

        testDeck = new ArrayList<Card>();
        testDeck.add(new Card("", 21, ""));
        user.take_card(testDeck, true);
        Dealer dealer = new Dealer();

        assertTrue(blackJack.userTurn(user, dealer, testDeck));


        user = new User();
        dealer = new Dealer();

        testDeck = new ArrayList<Card>();
        testDeck.add(new Card("", 22, ""));
        user.take_card(testDeck, true);

        assertTrue(blackJack.userTurn(user, dealer, testDeck));


        user = new User();
        dealer = new Dealer();

        testDeck = new ArrayList<>();
        testDeck.add(new Card("", 12, ""));
        testDeck.add(new Card("", 10, ""));
        testDeck.add(new Card("", 10, ""));
        user.take_card(testDeck, true);

        blackJack.in = new Scanner("1\n1\n");

        assertTrue(blackJack.userTurn(user, dealer, testDeck));


        user = new User();
        dealer = new Dealer();

        testDeck = new ArrayList<>();
        testDeck.add(new Card("", 2, ""));
        testDeck.add(new Card("", 2, ""));
        testDeck.add(new Card("", 2, ""));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        user.take_card(testDeck, true);

        blackJack.in = new Scanner("1\n0\n");

        assertFalse(blackJack.userTurn(user, dealer, testDeck));

    }

    @Test
    public void testDealTurn() {

        Dealer dealer = new Dealer();

        testDeck = new ArrayList<>();
        testDeck.add(new Card("", 21, ""));
        dealer.take_card(testDeck, true);

        User user = new User();
        blackJack.dealerTurn(user, dealer, testDeck);

        assertEquals(dealer.wins, 1);
        assertEquals(user.wins, 0);


        user = new User();
        dealer = new Dealer();

        testDeck = new ArrayList<>();
        testDeck.add(new Card("", 22, ""));
        dealer.take_card(testDeck, true);
        blackJack.dealerTurn(user, dealer, testDeck);

        assertEquals(dealer.wins, 0);
        assertEquals(user.wins, 1);


        user = new User();
        dealer = new Dealer();

        testDeck = new ArrayList<>();
        testDeck.add(new Card("", 10, ""));
        testDeck.add(new Card("", 17, ""));
        dealer.take_card(testDeck, true);
        blackJack.dealerTurn(user, dealer, testDeck);

        assertEquals(dealer.wins, 1);
        assertEquals(user.wins, 0);
    }
}
