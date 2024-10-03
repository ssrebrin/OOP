package ru.nsu.rebrin;

import java.util.Arrays;

/**
 * Card class.
 */
public class Card {
    public int[] openn;
    public String suit;
    public int meaning;
    public String value;

    /**
     * Init.
     *
     * @param suit    - suit
     * @param meaning - meaning
     * @param value   - value
     */
    public Card(String suit, int meaning, String value, int[] openn) {
        this.meaning = meaning;
        this.suit = suit;
        this.value = value;
        this.openn = openn;
    }

    /**
     * Equality relation.
     *
     * @param obj - object
     * @return - true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Card other = (Card) obj;
        return meaning == other.meaning
                && suit.equals(other.suit)
                && value.equals(other.value);
    }

    /**
     * Open card.
     */
    public void open(int[] openn) {
        this.openn = openn;
    }

    /**
     * Show card.
     *
     * @param flag - for ace
     */
    public void show(boolean flag, int player) {
        for (int i : this.openn) {
            if (i == player) {
                if (meaning == 11 && flag) {
                    System.out.print(suit + " " + value + " (1)");
                } else {
                    System.out.print(suit + " " + value + " (" + meaning + ")");
                }
                return;
            }
        }
        System.out.print("<Close card>");
    }

}