package ru.nsu.rebrin;

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

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

    public void show() {
        if (this.open) {
            System.out.print(suit + " " + value + " (" + meaning + ")");
        } else {
            System.out.print("<Close card>");
        }
    }

}