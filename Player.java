import java.util.ArrayList; // Import the ArrayList class

public class Player {
    // Private fields to store the player's cards, split hand, balance, bet, and split status
    private ArrayList<Card> hand; // List to store the player's cards
    private ArrayList<Card> splitHand; // List to store the player's second hand after a split
    private boolean hasSplit; // Boolean to track if the player has split their hand
    private int balance; // The player's balance of money
    private int bet; // The player's current bet amount

    // Constructor to create a player with a starting balance
    public Player(int balance) { 
        hand = new ArrayList<>(); // Initialize the player's hand
        this.balance = balance; // Set the player's balance
        this.hasSplit = false; // Set hasSplit to false initially
    }

    // Method to add a card to the player's hand
    public void addCard(Card card) { 
        hand.add(card); // Add the card to the hand
    }

    // Method to split the player's hand
    public void split() { 
        // Check if the hand can be split (must have exactly two cards of the same value)
        if (hand.size() == 2 && hand.get(0).getValue().equals(hand.get(1).getValue())) { 
            splitHand = new ArrayList<>(); // Initialize the split hand
            splitHand.add(hand.remove(1)); // Move one card to the split hand
            hasSplit = true; // Set hasSplit to true
        } else {
            throw new IllegalArgumentException("Cannot split. Conditions not met."); // Throw an error if the hand cannot be split
        }
    }

    // Method to get the player's hand
    public ArrayList<Card> getHand() { 
        return hand; // Return the hand
    }

    // Method to get the player's split hand
    public ArrayList<Card> getSplitHand() { 
        return splitHand; // Return the split hand
    }

    // Method to check if the player has split their hand
    public boolean hasSplit() { 
        return hasSplit; // Return the value of hasSplit
    }

    // Method to clear the player's hand
    public void clearHand() { 
        hand.clear(); // Clear the hand
        splitHand = null; // Reset the split hand
        hasSplit = false; // Reset hasSplit to false
    }

    // Method to clear the player's split hand
    public void clearSplitHand() { 
        splitHand = null; // Reset the split hand
        hasSplit = false; // Reset hasSplit to false
    }

    // Method to place a bet
    public void placeBet(int amount) { 
        if (amount > balance) { // Check if the bet is more than the player's balance
            throw new IllegalArgumentException("Bet amount exceeds balance"); // Throw an error if the bet is too high
        }
        bet = amount; // Set the bet amount
        balance -= amount; // Deduct the bet from the balance
    }

    // Method to double the bet
    public void doubleDown() { 
        if (bet * 2 > balance) { // Check if the doubled bet is more than the player's balance
            throw new IllegalArgumentException("Cannot double down, not enough balance"); // Throw an error if the bet is too high
        }
        balance -= bet; // Deduct the bet amount from the balance
        bet *= 2; // Double the bet amount
    }

    // Method to handle winning the bet
    public void winBet() { 
        balance += 2 * bet; // Add double the bet amount to the balance
        bet = 0; // Reset the bet amount
    }

    // Method to handle losing the bet
    public void loseBet() { 
        bet = 0; // Reset the bet amount
    }

    // Method to get the player's balance
    public int getBalance() { 
        return balance; // Return the balance
    }

    // Method to calculate the value of the hand
    public int getHandValue() { 
        int value = 0; // Initialize the value
        int aces = 0; // Initialize the number of aces

        for (Card card : hand) { // Loop through each card in the hand
            String cardValue = card.getValue(); // Get the value of the card
            if (cardValue.equals("Jack") || cardValue.equals("Queen") || cardValue.equals("King")) { // Check if the card is a face card
                value += 10; // Add 10 to the value
            } else if (cardValue.equals("Ace")) { // Check if the card is an ace
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
}

/*
 * Summary:
 * The Player class models a player in a card game, providing functionality for managing the player's hand(s),
 * placing bets, and calculating hand values. The class supports actions like splitting hands, doubling down, and 
 * clearing hands between rounds. It also ensures proper betting by validating that the player has sufficient balance 
 * before placing or doubling a bet. The class handles common game mechanics such as adjusting hand values for aces 
 * in games like Blackjack, where aces can count as 1 or 11 depending on the hand's total value.
 */
