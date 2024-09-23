package ru.nsu.rebrin;

/**
 * Card class.
 */
public class Card {
    public boolean open;
    public String suit;
    public int meaning;
    public String value;

    public Card(String suit, int meaning, String value) {
        this.meaning = meaning;
        this.suit = suit;
        this.value = value;
        this.open = false;
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
        if (obj == null || getClass() != obj.getClass()) {
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
    public void open() {
        this.open = true;
    }

    /**
     * Close card.
     */
    public void close() {
        this.open = false;
    }

    /**
     * Print card.
     */
    public void show() {
        if (this.open) {
            System.out.print(suit + " " + value + " (" + meaning + ")");
        } else {
            System.out.print("<Close card>");
        }
    }

}