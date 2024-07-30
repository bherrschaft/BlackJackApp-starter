import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    private Deck deck;
    private Player player;
    private Player dealer;

    public GameRunner() {
        deck = new Deck();
        deck.shuffle();
        player = new Player(100); // Starting balance
        dealer = new Player(0); // Dealer does not need a balance
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Your balance: $" + player.getBalance());
            System.out.print("Enter your bet (increments of 5): ");
            int bet = scanner.nextInt();

            while (bet % 5 != 0) {
                System.out.print("Invalid bet. Enter your bet (increments of 5): ");
                bet = scanner.nextInt();
            }

            player.placeBet(bet);

            player.addCard(deck.deal());
            player.addCard(deck.deal());
            dealer.addCard(deck.deal());
            dealer.addCard(deck.deal());

            System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")");
            System.out.println("Dealer's hand: [" + dealer.getHand().get(0) + ", ?]");

            boolean playerTurn = true;
            boolean doubledDown = false;
            boolean playerSplit = false;
            while (playerTurn && player.getHandValue() < 21) {
                System.out.print("Do you want to (1) hit, (2) stand, (3) double down, or (4) split? ");
                int action = scanner.nextInt();

                if (action == 1) {
                    player.addCard(deck.deal());
                    System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                } else if (action == 3) {
                    player.doubleDown();
                    doubledDown = true;
                    System.out.println("Your hand after doubling down: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                } else if (action == 4) {
                    try {
                        player.split();
                        System.out.println("Your split hands: " + player.getHand() + " and " + player.getSplitHand());
                        playerSplit = true;
                        playerTurn = false;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    playerTurn = false;
                }
            }

            // Handle hitting or standing after doubling down
            if (doubledDown) {
                playerTurn = true;
                while (playerTurn && player.getHandValue() < 21) {
                    System.out.print("Do you want to (1) hit or (2) stand? ");
                    int action = scanner.nextInt();

                    if (action == 1) {
                        player.addCard(deck.deal());
                        System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                    } else {
                        playerTurn = false;
                    }
                }
            }

            // Process split hand
            if (playerSplit) {
                playerTurn = true;
                System.out.println("Playing first hand...");
                while (playerTurn && player.getHandValue() < 21) {
                    System.out.print("Do you want to (1) hit or (2) stand? ");
                    int action = scanner.nextInt();

                    if (action == 1) {
                        player.addCard(deck.deal());
                        System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                    } else {
                        playerTurn = false;
                    }
                }

                playerTurn = true;
                System.out.println("Playing second hand...");
                while (playerTurn && calculateHandValue(player.getSplitHand()) < 21) {
                    System.out.print("Do you want to (1) hit or (2) stand? ");
                    int action = scanner.nextInt();

                    if (action == 1) {
                        player.getSplitHand().add(deck.deal());
                        System.out.println("Your split hand: " + player.getSplitHand() + " (value: " + calculateHandValue(player.getSplitHand()) + ")");
                    } else {
                        playerTurn = false;
                    }
                }
            }

            while (dealer.getHandValue() < 17) {
                dealer.addCard(deck.deal());
            }

            System.out.println("Dealer's hand: " + dealer.getHand() + " (value: " + dealer.getHandValue() + ")");

            if (playerSplit) {
                determineWinner(player.getHand(), dealer);
                determineWinner(player.getSplitHand(), dealer);
            } else {
                determineWinner(player.getHand(), dealer);
            }

            player.clearHand();
            player.clearSplitHand();
            dealer.clearHand();

            System.out.print("Do you want to play again? (yes/no): ");
            if (!scanner.next().equalsIgnoreCase("yes")) {
                break;
            }
        }

        scanner.close();
    }

    private void determineWinner(ArrayList<Card> hand, Player dealer) {
        int playerValue = calculateHandValue(hand);
        int dealerValue = dealer.getHandValue();

        if (playerValue > 21 || (dealerValue <= 21 && dealerValue > playerValue)) {
            System.out.println("You lose with hand " + hand + "!");
            player.loseBet();
        } else if (dealerValue > 21 || playerValue > dealerValue) {
            System.out.println("You win with hand " + hand + "!");
            player.winBet();
        } else {
            System.out.println("It's a tie with hand " + hand + "!");
            player.winBet(); // Return the bet
        }
    }

    private int calculateHandValue(ArrayList<Card> hand) {
        int value = 0;
        int aces = 0;

        for (Card card : hand) {
            String cardValue = card.getValue();
            if (cardValue.equals("Jack") || cardValue.equals("Queen") || cardValue.equals("King")) {
                value += 10;
            } else if (cardValue.equals("Ace")) {
                aces++;
                value += 11;
            } else {
                value += Integer.parseInt(cardValue);
            }
        }

        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }

        return value;
    }

    public static void main(String[] args) {
        GameRunner game = new GameRunner();
        game.playGame();
    }
}
