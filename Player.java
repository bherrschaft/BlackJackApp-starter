import java.util.ArrayList; // Import the ArrayList class

public class Player {
    private ArrayList<Card> hand; // List to store the player's cards
    private ArrayList<Card> splitHand; // List to store the player's second hand after a split
    private boolean hasSplit; // Boolean to track if the player has split their hand
    private int balance; // The player's balance of money
    private int bet; // The player's current bet amount

    public Player(int balance) { // Constructor to create a player with a starting balance
        hand = new ArrayList<>(); // Initialize the player's hand
        this.balance = balance; // Set the player's balance
        this.hasSplit = false; // Set hasSplit to false initially
    }

    public void addCard(Card card) { // Method to add a card to the player's hand
        hand.add(card); // Add the card to the hand
    }

    public void split() { // Method to split the player's hand
        if (hand.size() == 2 && hand.get(0).getValue().equals(hand.get(1).getValue())) { // Check if the hand can be split
            splitHand = new ArrayList<>(); // Initialize the split hand
            splitHand.add(hand.remove(1)); // Move one card to the split hand
            hasSplit = true; // Set hasSplit to true
        } else {
            throw new IllegalArgumentException("Cannot split. Conditions not met."); // Throw an error if the hand cannot be split
        }
    }

    public ArrayList<Card> getHand() { // Method to get the player's hand
        return hand; // Return the hand
    }

    public ArrayList<Card> getSplitHand() { // Method to get the player's split hand
        return splitHand; // Return the split hand
    }

    public boolean hasSplit() { // Method to check if the player has split their hand
        return hasSplit; // Return the value of hasSplit
    }

    public void clearHand() { // Method to clear the player's hand
        hand.clear(); // Clear the hand
        splitHand = null; // Reset the split hand
        hasSplit = false; // Reset hasSplit to false
    }

    public void clearSplitHand() { // Method to clear the player's split hand
        splitHand = null; // Reset the split hand
        hasSplit = false; // Reset hasSplit to false
    }

    public void placeBet(int amount) { // Method to place a bet
        if (amount > balance) { // Check if the bet is more than the player's balance
            throw new IllegalArgumentException("Bet amount exceeds balance"); // Throw an error if the bet is too high
        }
        bet = amount; // Set the bet amount
        balance -= amount; // Deduct the bet from the balance
    }

    public void doubleDown() { // Method to double the bet
        if (bet * 2 > balance) { // Check if the doubled bet is more than the player's balance
            throw new IllegalArgumentException("Cannot double down, not enough balance"); // Throw an error if the bet is too high
        }
        balance -= bet; // Deduct the bet amount from the balance
        bet *= 2; // Double the bet amount
    }

    public void winBet() { // Method to handle winning the bet
        balance += 2 * bet; // Add double the bet amount to the balance
        bet = 0; // Reset the bet amount
    }

    public void loseBet() { // Method to handle losing the bet
        bet = 0; // Reset the bet amount
    }

    public int getBalance() { // Method to get the player's balance
        return balance; // Return the balance
    }

    public int getHandValue() { // Method to calculate the value of the hand
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

