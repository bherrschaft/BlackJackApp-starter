public class Card {
    // Private fields to store the suit and value of the card
    private String suit;  // The suit of the card (Hearts, Diamonds, Clubs, Spades)
    private String value; // The value of the card (2-10, Jack, Queen, King, Ace)

    // Constructor to create a card with a specific suit and value
    public Card(String suit, String value) {
        this.suit = suit;  // Set the suit of the card with the provided argument
        this.value = value;  // Set the value of the card with the provided argument
    }

    // Method to get the suit of the card
    public String getSuit() {
        return suit; // Returns the suit of the card
    }

    // Method to get the value of the card
    public String getValue() {
        return value; // Returns the value of the card
    }

    // Override the toString method to get a string representation of the card
    @Override
    public String toString() {
        // Returns a string like "Ace of Spades"
        return value + " of " + suit; 
    }
}

/*
 * Summary:
 * The Card class models a playing card with two attributes: suit and value. The suit represents one of the four 
 * card suits (Hearts, Diamonds, Clubs, Spades), and the value represents the card's rank (2-10, Jack, Queen, King, Ace).
 * The class includes methods to retrieve the suit and value of a card, and it overrides the toString method to provide 
 * a user-friendly string representation of the card, such as "Ace of Spades". This class is commonly used in card game 
 * simulations and other applications where a standard deck of cards is required.
 */
