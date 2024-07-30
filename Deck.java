import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards; // List to store all the cards in the deck

    public Deck() { // Constructor to create a new deck of 52 cards
        cards = new ArrayList<>(); // Initialize the list of cards
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"}; // Array of suits
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"}; // Array of values

        for (String suit : suits) { // Loop through each suit
            for (String value : values) { // Loop through each value
                cards.add(new Card(suit, value)); // Add a new card with the current suit and value to the deck
            }
        }
    }

    public void shuffle() { // Method to shuffle the deck
        Collections.shuffle(cards); // Shuffle the list of cards
    }

    public Card deal() { // Method to deal a card from the top of the deck
        return cards.remove(0); // Remove and return the first card from the list
    }

    public int size() { // Method to get the number of cards left in the deck
        return cards.size(); // Return the size of the list
    }
}
