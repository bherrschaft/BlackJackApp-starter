public class Card {
    private String suit;  // The suit of the card (Hearts, Diamonds, Clubs, Spades)
    private String value; // The value of the card (2-10, Jack, Queen, King, Ace)

    public Card(String suit, String value) { // Constructor to create a card with a specific suit and value
        this.suit = suit;  // Set the suit of the card
        this.value = value;  // Set the value of the card
    }

    public String getSuit() { // Method to get the suit of the card
        return suit; // Returns the suit of the card
    }

    public String getValue() { // Method to get the value of the card
        return value; // Returns the value of the card
    }

    @Override
    public String toString() { // Method to get a string representation of the card
        return value + " of " + suit; // Returns a string like "Ace of Spades"
    }
}
