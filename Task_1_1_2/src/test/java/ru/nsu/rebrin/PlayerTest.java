package ru.nsu.rebrin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    BlackJack main = new BlackJack();
    User user = new User();
    Dealer dealer = new Dealer();
    List<Card> deck = main.prepare_deck();

    @Test
    public void Test1() {
        user.take_card(deck, true);
        user.take_card(deck, true);
        user.take_card(deck, false);


        assertTrue(user.score <= 30);

        user.clear_hand(deck);
        assertTrue(user.hand.isEmpty());
        assertEquals(deck.size(), 52);
    }
}