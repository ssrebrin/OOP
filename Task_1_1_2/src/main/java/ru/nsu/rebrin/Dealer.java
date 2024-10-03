package ru.nsu.rebrin;

/**
 * Dealer class.
 */
public class Dealer extends Player {

    /**
     * Print hand.
     *
     * @param withScore - true if with score
     */
    public void show_hand(boolean withScore, int player) {

        boolean flag = false;

        if (withScore) {
            int sum = 0;
            for (Card c : this.hand) {
                sum += c.meaning;
            }
            if (sum > 21) {
                flag = true;
            }
        }

        System.out.print("Dealer's cards: ");
        System.out.print("[");
        for (Card c : this.hand) {
            c.show(withScore && flag, player);
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
