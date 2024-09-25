package ru.nsu.rebrin;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * The game.
 */
public class BlackJack {

    Scanner in = new Scanner(System.in);

    /**
     * Create the deck and shuffle it.
     *
     * @return - shuffled deck
     */
    public List<Card> prepare_deck() {
        List<Card> deck = new ArrayList<>(52);
        addCards(deck);
        shuffle(deck);
        return deck;
    }

    /**
     * Add cards to the deck.
     *
     * @param deck the deck to which cards will be added
     */
    void addCards(List<Card> deck) {
        String[] ranks = {"Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
        String[] suits = {"Spades", "Hearts", "Clubs", "Diamonds"};

        for (int i = 0; i < ranks.length; i++) {
            for (String suit : suits) {
                deck.add(new Card(ranks[i], values[i], suit));
            }
        }
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
     * Waiting for correct input.
     *
     * @return true if entered 1 and false if 0
     */
    public boolean scan() {
        int input;
        while (true) {
            try {
                input = in.nextInt();
                if (input == 1) {
                    return true;
                } else if (input == 0) {
                    return false;
                } else {
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
    public void sleep(int n) {
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            System.err.println("Поток был прерван: " + e.getMessage());
        }
    }

    /**
     * Starts a new round of the game.
     *
     * @param deck   the deck of cards
     * @param user   the user
     * @param dealer the dealer
     * @return true if the game should continue, false otherwise
     */
    boolean playRound(List<Card> deck, User user, Dealer dealer) {
        user.clear_hand(deck);
        dealer.clear_hand(deck);
        shuffle(deck);

        user.take_card(deck, true);
        user.take_card(deck, true);
        dealer.take_card(deck, true);
        dealer.take_card(deck, false);
        dealer.points();

        user.show_hand(true);
        dealer.show_hand(false);

        if (userTurn(user, dealer, deck)) return askForContinue();

        dealerTurn(user, dealer, deck);

        printScore(user, dealer);
        return askForContinue();
    }

    /**
     * Handles the user's turn.
     *
     * @param user   the user
     * @param dealer the dealer
     * @param deck   the deck of cards
     * @return true if the round ends early, false otherwise
     */
    boolean userTurn(User user, Dealer dealer, List<Card> deck) {
        while (true) {
            user.points();
            if (user.score == 21) {
                System.out.println("You hit BlackJack. You win!!!");
                sleep(1);
                user.wins++;
                return true;
            }

            if (user.score > 21) {
                System.out.println("You have exceeded 21. You lose(");
                sleep(1);
                dealer.wins++;
                return true;
            }

            System.out.println("Input a number");
            sleep(1);

            if (scan()) {
                System.out.print("You take a card ");
                user.take_card(deck, true).show(false);
                sleep(1);
                System.out.println();
                user.show_hand(true);
                dealer.show_hand(false);
                sleep(1);
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Handles the dealer's turn.
     *
     * @param user   the user
     * @param dealer the dealer
     * @param deck   the deck of cards
     */
    void dealerTurn(User user, Dealer dealer, List<Card> deck) {
        System.out.println("Dealer move\n------------");
        sleep(1);
        System.out.print("The dealer reveals a close card");
        sleep(1);
        dealer.hand.get(dealer.hand.size() - 1).open();
        dealer.hand.get(dealer.hand.size() - 1).show(false);
        System.out.println();

        user.show_hand(true);
        dealer.show_hand(true);

        while (true) {
            dealer.points();
            if (dealer.score == 21) {
                System.out.println("Dealer hit BlackJack. Dealer win!");
                sleep(1);
                dealer.wins++;
                break;
            }

            if (dealer.score > 21) {
                System.out.println("Dealer has exceeded 21. Dealer lose");
                sleep(1);
                user.wins++;
                break;
            }

            if (dealer.score >= 17) {
                determineWinner(user, dealer);
                break;
            }

            sleep(2);
            System.out.print("Dealer takes a card ");
            dealer.take_card(deck, true).show(false);
            sleep(2);
            System.out.println();
            user.show_hand(true);
            dealer.show_hand(true);
            sleep(1);
        }
    }

    /**
     * Determines the winner based on the scores.
     *
     * @param user   the user
     * @param dealer the dealer
     */
    void determineWinner(User user, Dealer dealer) {
        System.out.print("Game over. ");
        sleep(1);

        if (dealer.score > user.score) {
            System.out.println("You lose.");
            sleep(1);
            dealer.wins++;
        } else if (dealer.score < user.score) {
            System.out.println("You win.");
            sleep(1);
            user.wins++;
        } else {
            System.out.println("Dead heat.");
            sleep(1);
            dealer.wins++;
            user.wins++;
        }
    }

    /**
     * Prints the current score.
     *
     * @param user   the user
     * @param dealer the dealer
     */
    void printScore(User user, Dealer dealer) {
        System.out.println("Score: " + user.wins + "-" + dealer.wins);
        sleep(1);
    }

    /**
     * Asks the player if they want to continue playing.
     *
     * @return true if the player wants to continue, false otherwise
     */
    boolean askForContinue() {
        System.out.println("Enter 1 if you want to continue and 0 if not.");
        sleep(1);
        return scan();
    }

    /**
     * Main game.
     *
     * @param args - args
     */
    public static void main(String[] args) {
        BlackJack game = new BlackJack();
        List<Card> deck = game.prepare_deck();

        User user = new User();
        Dealer dealer = new Dealer();

        System.out.println("Welcome to BlackJack");
        game.sleep(1);

        int rounds = 1;
        while (true) {
            System.out.println("Round " + (rounds));
            game.sleep(1);
            rounds++;

            if (!game.playRound(deck, user, dealer)) {
                break;
            }
            System.out.println("==============================");
        }
    }
}
