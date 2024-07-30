import java.util.ArrayList;
import java.util.Scanner;

public class GameRunner {
    private Deck deck; // The deck of cards
    private Player player; // The player
    private Player dealer; // The dealer

    public GameRunner() { // Constructor to initialize the game
        deck = new Deck(); // Create a new deck
        deck.shuffle(); // Shuffle the deck
        player = new Player(100); // Create a new player with a starting balance of 100
        dealer = new Player(0); // Create a new dealer (no balance needed)
    }

    public void playGame() { // Method to start and manage the game
        Scanner scanner = new Scanner(System.in); // Scanner to read user input

        while (true) { // Main game loop
            System.out.println("Your balance: $" + player.getBalance()); // Display the player's balance
            System.out.print("Enter your bet (increments of 5): "); // Prompt for bet amount
            int bet = scanner.nextInt(); // Read the player's bet

            while (bet % 5 != 0) { // Ensure the bet is in increments of 5
                System.out.print("Invalid bet. Enter your bet (increments of 5): ");
                bet = scanner.nextInt(); // Read the bet again
            }

            player.placeBet(bet); // Place the bet

            player.addCard(deck.deal()); // Deal two cards to the player
            player.addCard(deck.deal());
            dealer.addCard(deck.deal()); // Deal two cards to the dealer
            dealer.addCard(deck.deal());

            System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")"); // Show the player's hand
            System.out.println("Dealer's hand: [" + dealer.getHand().get(0) + ", ?]"); // Show one of the dealer's cards

            boolean playerTurn = true; // Flag for the player's turn
            boolean doubledDown = false; // Flag for doubling down
            boolean playerSplit = false; // Flag for splitting

            while (playerTurn && player.getHandValue() < 21) { // Player's turn loop
                System.out.print("Do you want to (1) hit, (2) stand, (3) double down, or (4) split? ");
                int action = scanner.nextInt(); // Read the player's action

                if (action == 1) { // Player chooses to hit
                    player.addCard(deck.deal()); // Deal a card to the player
                    System.out.println("Your hand: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                } else if (action == 3) { // Player chooses to double down
                    player.doubleDown(); // Double the bet
                    doubledDown = true; // Set the doubled down flag
                    System.out.println("Your hand after doubling down: " + player.getHand() + " (value: " + player.getHandValue() + ")");
                    playerTurn = false; // End the player's turn
                } else if (action == 4) { // Player chooses to split
                    try {
                        player.split(); // Split the player's hand
                        System.out.println("Your split hands: " + player.getHand() + " and " + player.getSplitHand());
                        playerSplit = true; // Set the split flag
                        playerTurn = false; // End the player's turn
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage()); // Print the error message
                    }
                } else {
                    playerTurn = false; // End the player's turn
                }
            }

            // Handle hitting or standing after doubling down
            if (doubledDown) { // If the player doubled down
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

            while (dealer.getHandValue() < 17) { // Dealer's turn
                dealer.addCard(deck.deal()); // Dealer hits until hand value is at least 17
            }

            System.out.println("Dealer's hand: " + dealer.getHand() + " (value: " + dealer.getHandValue() + ")"); // Show the dealer's hand

            if (playerSplit) { // Determine the winner for both hands if the player split
                determineWinner(player.getHand(), dealer);
                determineWinner(player.getSplitHand(), dealer);
            } else {
                determineWinner(player.getHand(), dealer); // Determine the winner for a single hand
            }

            player.clearHand(); // Clear the player's hand for the next round
            player.clearSplitHand(); // Clear the player's split hand if there was one
            dealer.clearHand(); // Clear the dealer's hand

            System.out.print("Do you want to play again? (yes/no): "); // Ask the player if they want to play again
            if (!scanner.next().equalsIgnoreCase("yes")) { // End the game loop if the player says no
                break;
            }
        }

        scanner.close(); // Close the scanner
    }

    private void determineWinner(ArrayList<Card> hand, Player dealer) { // Method to determine the winner
        int playerValue = calculateHandValue(hand); // Calculate the player's hand value
        int dealerValue = dealer.getHandValue(); // Get the dealer's hand value

        if (playerValue > 21 || (dealerValue <= 21 && dealerValue > playerValue)) { // Player busts or dealer wins
            System.out.println("You lose with hand " + hand + "!");
            player.loseBet(); // Player loses the bet
        } else if (dealerValue > 21 || playerValue > dealerValue) { // Dealer busts or player wins
            System.out.println("You win with hand " + hand + "!");
            player.winBet(); // Player wins the bet
        } else { // Tie
            System.out.println("It's a tie with hand " + hand + "!");
            player.winBet(); // Player gets their bet back
        }
    }

    private int calculateHandValue(ArrayList<Card> hand) { // Method to calculate the hand value
        int value = 0; // Initialize the value
        int aces = 0; // Initialize the number of aces

        for (Card card : hand) { // Loop through each card in the hand
            String cardValue = card.getValue(); // Get the value of the card
            if (cardValue.equals("Jack") || cardValue.equals("Queen") || cardValue.equals("King")) { // Face cards
                value += 10; // Add 10 to the value
            } else if (cardValue.equals("Ace")) { // Ace
                aces++; // Increment the number of aces
                value += 11; // Add 11 to the value
            } else {
                value += Integer.parseInt(cardValue); // Add the numeric value of the card
            }
        }

        while (value > 21 && aces > 0) { // Adjust for aces if the value is over 21
            value -= 10; // Subtract 10 from the value
            aces--; // Decrement the number of aces
        }

        return value; // Return the value of the hand
    }

    public static void main(String[] args) { // Main method to start the game
        GameRunner game = new GameRunner(); // Create a new game
        game.playGame(); // Start the game
    }
}



