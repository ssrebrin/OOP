package ru.nsu.rebrin;

public class Dealer extends Player{

    public void show_hand(boolean with_score){

        System.out.print("Dealer's cards: ");
        System.out.print("[");
        for (Card c : this.hand){
            c.show();
            if (c != this.hand.getLast())
                System.out.print(", ");
        }
        System.out.print("]");

        if(with_score){
            this.points();
            System.out.print(" => " + this.score);
        }

        System.out.println();

    }
}
