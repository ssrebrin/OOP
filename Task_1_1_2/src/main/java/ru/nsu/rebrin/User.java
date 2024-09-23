package ru.nsu.rebrin;

/**
 * Player class.
 */
public class User extends Player {

    /**
     * Print hand.
     *
     * @param withScore - true if with score
     */
    public void show_hand(boolean withScore) {

        System.out.print("Your hand: ");
        System.out.print("[");
        for (Card c : this.hand) {
            c.show();
            if (c != this.hand.get(this.hand.size() - 1)) {
                System.out.print(", ");
            }
        }
        System.out.print("]");

        if (withScore) {
            this.points();
            System.out.print(" => " + this.score);
        }

        System.out.println();

    }
}