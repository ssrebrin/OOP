package ru.nsu.rebrin;

import java.util.InputMismatchException;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The game.
 */
public class BlackJack {

    Scanner in = new Scanner(System.in);

    /**
     * Create the deck and shuffle it.
     *
     * @return - shaffled deck
     */
    public List<Card> prepare_deck() {
        List<Card> deck = new ArrayList<Card>(52);
        {
            deck.add(0, new Card("Two", 2, "Spades"));
            deck.add(1, new Card("Two", 2, "Hearts"));
            deck.add(2, new Card("Two", 2, "Clubs"));
            deck.add(3, new Card("Two", 2, "Diamonds"));

            deck.add(4, new Card("Three", 3, "Spades"));
            deck.add(5, new Card("Three", 3, "Hearts"));
            deck.add(6, new Card("Three", 3, "Clubs"));
            deck.add(7, new Card("Three", 3, "Diamonds"));

            deck.add(8, new Card("Four", 4, "Spades"));
            deck.add(9, new Card("Four", 4, "Hearts"));
            deck.add(10, new Card("Four", 4, "Clubs"));
            deck.add(11, new Card("Four", 4, "Diamonds"));

            deck.add(12, new Card("Five", 5, "Spades"));
            deck.add(13, new Card("Five", 5, "Hearts"));
            deck.add(14, new Card("Five", 5, "Clubs"));
            deck.add(15, new Card("Five", 5, "Diamonds"));

            deck.add(16, new Card("Six", 6, "Spades"));
            deck.add(17, new Card("Six", 6, "Hearts"));
            deck.add(18, new Card("Six", 6, "Clubs"));
            deck.add(19, new Card("Six", 6, "Diamonds"));

            deck.add(20, new Card("Seven", 7, "Spades"));
            deck.add(21, new Card("Seven", 7, "Hearts"));
            deck.add(22, new Card("Seven", 7, "Clubs"));
            deck.add(23, new Card("Seven", 7, "Diamonds"));

            deck.add(24, new Card("Eight", 8, "Spades"));
            deck.add(25, new Card("Eight", 8, "Hearts"));
            deck.add(26, new Card("Eight", 8, "Clubs"));
            deck.add(27, new Card("Eight", 8, "Diamonds"));

            deck.add(28, new Card("Nine", 9, "Spades"));
            deck.add(29, new Card("Nine", 9, "Hearts"));
            deck.add(30, new Card("Nine", 9, "Clubs"));
            deck.add(31, new Card("Nine", 9, "Diamonds"));

            deck.add(32, new Card("Ten", 10, "Spades"));
            deck.add(33, new Card("Ten", 10, "Hearts"));
            deck.add(34, new Card("Ten", 10, "Clubs"));
            deck.add(35, new Card("Ten", 10, "Diamonds"));

            deck.add(36, new Card("Jack", 10, "Spades"));
            deck.add(37, new Card("Jack", 10, "Hearts"));
            deck.add(38, new Card("Jack", 10, "Clubs"));
            deck.add(39, new Card("Jack", 10, "Diamonds"));

            deck.add(40, new Card("Queen", 10, "Spades"));
            deck.add(41, new Card("Queen", 10, "Hearts"));
            deck.add(42, new Card("Queen", 10, "Clubs"));
            deck.add(43, new Card("Queen", 10, "Diamonds"));

            deck.add(44, new Card("King", 10, "Spades"));
            deck.add(45, new Card("King", 10, "Hearts"));
            deck.add(46, new Card("King", 10, "Clubs"));
            deck.add(47, new Card("King", 10, "Diamonds"));

            deck.add(48, new Card("Ace", 11, "Spades"));
            deck.add(49, new Card("Ace", 11, "Hearts"));
            deck.add(50, new Card("Ace", 11, "Clubs"));
            deck.add(51, new Card("Ace", 11, "Diamonds"));
        }

        shuffle(deck);

        return deck;
    }

    /**
     * Shuffling the deck.
     *
     * @param deck - deck
     */
    public void shuffle(List<Card> deck) {
        Card temp;

        for (int i = 51; i >= 0; i--) {
            int randomIndex = (int) ((Math.random()) * (i + 1));
            temp = deck.get(i);
            deck.set(i, deck.get(randomIndex));
            deck.set(randomIndex, temp);

        }
    }

    /**
     * Waiting correct input.
     *
     * @return true if entered 1 and false if 0
     */
    public boolean scan() {
        int input;
        while (true) {
            try {
                input = in.nextInt();
                if (input == 1) return true;
                else if (input == 0) return false;
                else {
                    System.out.println("Input 1 or 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input 1 or 0.");
                in.next(); // Очистка буфера ввода
            }
        }
    }


    /**
     * Waiting n seconds.
     *
     * @param n number of seconds
     */
    public void slp(int n) {

        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            System.err.println("Поток был прерван: " + e.getMessage());
        }
    }

    /**
     * Main game.
     *
     * @param args
     */
    public static void main(String[] args) {

        boolean end;
        int input;
        BlackJack main = new BlackJack();
        List<Card> deck = main.prepare_deck();

        User user = new User();
        Dealer dealer = new Dealer();

        System.out.println("Welcome to BlackJack");
        main.slp(1);
        int rounds = 1;
        //Игра
        while (true) {
            System.out.println("Round " + (rounds));
            main.slp(1);
            rounds++;
            end = false;

            user.clear_hand(deck);
            dealer.clear_hand(deck);
            main.shuffle(deck);

            user.take_card(deck, true);
            user.take_card(deck, true);
            dealer.take_card(deck, true);
            dealer.take_card(deck, false);
            dealer.points();

            user.show_hand(true);
            dealer.show_hand(false);

            //Ход игрока
            while (!end) {
                user.points();
                if (user.score == 21) {
                    System.out.println("You hit BlackJack. You win!!!");
                    main.slp(1);
                    end = true;
                    user.wins++;
                    break;
                }

                if (user.score > 21) {
                    System.out.println("You have exceeded 21. You lose(");
                    main.slp(1);
                    end = true;
                    dealer.wins++;
                    break;
                }

                System.out.println("Input a number");
                main.slp(1);

                if (main.scan()) {
                    System.out.print("You take a card ");
                    user.take_card(deck, true).show();
                    main.slp(1);
                    System.out.println();
                    user.show_hand(true);
                    dealer.show_hand(false);
                    main.slp(1);
                } else
                    break;
            }

            //ход дилера
            if (!end) {
                System.out.println("Dealer move\n------------");
                main.slp(1);
                System.out.print("The dealer reveals a close card");
                main.slp(1);
                dealer.hand.get(dealer.hand.size() - 1).open();
                dealer.hand.get(dealer.hand.size() - 1).show();
                System.out.println();

                user.show_hand(true);
                dealer.show_hand(true);
            }

            while (!end) {

                dealer.points();
                if (dealer.score == 21) {
                    System.out.println("Dealer hit BlackJack. Dealer win!");
                    main.slp(1);
                    end = true;
                    dealer.wins++;
                    break;
                }

                if (dealer.score > 21) {
                    System.out.println("Dealer have exceeded 21. Dealer lose");
                    main.slp(1);
                    end = true;
                    user.wins++;
                    break;
                }

                if (dealer.score >= 17) {
                    System.out.print("Game over. ");
                    main.slp(1);

                    if (dealer.score > user.score) {
                        System.out.println("You lose.");
                        main.slp(1);
                        dealer.wins++;
                    }
                    if (dealer.score < user.score) {
                        System.out.println("You win.");
                        main.slp(1);
                        user.wins++;
                    }
                    if (dealer.score == user.score) {
                        System.out.println("Dead heat.");
                        main.slp(1);
                        dealer.wins++;
                        user.wins++;
                    }
                    break;
                }


                main.slp(2);
                System.out.print("Dealer take a card ");

                dealer.take_card(deck, true).show();
                main.slp(2);
                System.out.println();
                user.show_hand(true);
                dealer.show_hand(true);
                main.slp(1);

            }

            System.out.println("Score: " + user.wins + "-" + dealer.wins);
            main.slp(1);
            System.out.println("Enter 1 if you want to continue and 0 if not.");
            main.slp(1);

            if (!main.scan()) {
                break;
            }
            System.out.println("==============================");
        }

    }
}