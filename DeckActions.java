public interface DeckActions {
    // Method to shuffle the deck of cards
    public void shuffle(); // Shuffles the cards in the deck

    // Method to deal the next card from the deck
    public Card dealNextCard(); // Deals the next card from the top of the deck

    // Method to print a specified number of cards from the deck
    public void printDeck(int numToPrint); // Prints a specified number of cards from the deck
}

/*
 * Summary:
 * The DeckActions interface defines the essential actions that can be performed on a deck of cards. 
 * It includes methods to shuffle the deck, deal the next card, and print a specified number of cards. 
 * Any class that implements this interface must provide concrete implementations of these methods. 
 * This interface is particularly useful in card game applications where decks need to be manipulated in a standard way.
 */
