package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.List;

/**
 * Player superclass.
 */
public class Player {
    int score = 0;
    int wins = 0;
    List<Card> hand = new ArrayList<>();

    /**
     * Player take a card.
     *
     * @param deck - deck
     * @param open - true if card should be face down
     * @return - taken card
     */
    public Card take_card(List<Card> deck, boolean open) {
        Card card = deck.get(deck.size() - 1);
        deck.remove(deck.size() - 1);
        if (open) {
            card.open();
        }
        else {
            card.close();
        }
        this.hand.add(card);
        return card;
    }

    /**
     * Free hand.
     *
     * @param deck - deck
     */
    public void clear_hand(List<Card> deck) {
        while (!this.hand.isEmpty()) {
            Card card = this.hand.get(this.hand.size() - 1);
            this.hand.remove(this.hand.size() - 1);
            deck.add(card);
        }
    }

    /**
     * Count points of player.
     */
    public void points() {
        int aceCnt = 0;
        int sc = 0;
        for (Card c : this.hand) {
            if (c.meaning == 11) {
                aceCnt++;
            }
            sc += c.meaning;
        }
        if (sc > 21) {
            sc -= aceCnt * 10;
        }
        this.score = sc;
    }

}
