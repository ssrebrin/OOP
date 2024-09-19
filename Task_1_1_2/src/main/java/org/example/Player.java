package org.example;

import java.util.ArrayList;
import java.util.List;

public class Player {
    int score = 0;
    int wins = 0;
    List<Card> hand = new ArrayList<>();


    public Card take_card(List<Card> deck, boolean open) {
        Card card;
        card = deck.removeLast();
        if (open)
            card.open();
        else
            card.close();
        this.hand.add(card);
        return card;
    }

    public void clear_hand(List<Card> deck){
        while (!deck.isEmpty()){
            deck.add(deck.removeLast());
        }
    }

    public void points() {
        int ace_cnt = 0;
        int sc = 0;
        for (Card c : this.hand) {
            if (c.meaning == 11) {
                ace_cnt++;
            }
            sc += c.meaning;
        }
        if (sc > 21)
            sc -= ace_cnt * 10;
        this.score = sc;
    }

}
