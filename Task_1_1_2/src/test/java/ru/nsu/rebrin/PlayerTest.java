package ru.nsu.rebrin;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class PlayerTest {

    BlackJack main = new BlackJack();
    User user = new User();
    Dealer dealer = new Dealer();
    List<Card> deck = main.prepare_deck();

    @Test
    public void test1() {
        user.take_card(deck, true);
        user.take_card(deck, true);
        user.take_card(deck, false);


        assertTrue(user.score <= 30);

        user.clear_hand(deck);
        assertTrue(user.hand.isEmpty());
        assertEquals(deck.size(), 52);
    }
}