package ru.nsu.rebrin;

/**
 * Dealer class.
 */
public class Dealer extends Player {

    /**
     * Print hand.
     *
     * @param with_score - true if with score
     */
    public void show_hand(boolean with_score) {

        System.out.print("Dealer's cards: ");
        System.out.print("[");
        for (Card c : this.hand) {
            c.show();
            if (c != this.hand.get(this.hand.size() - 1))
                System.out.print(", ");
        }
        System.out.print("]");

        if (with_score) {
            this.points();
            System.out.print(" => " + this.score);
        }

        System.out.println();

    }
}
