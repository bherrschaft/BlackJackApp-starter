import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> splitHand;
    private boolean hasSplit;
    private int balance;
    private int bet;

    public Player(int balance) {
        hand = new ArrayList<>();
        this.balance = balance;
        this.hasSplit = false;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void split() {
        if (hand.size() == 2 && hand.get(0).getValue().equals(hand.get(1).getValue())) {
            splitHand = new ArrayList<>();
            splitHand.add(hand.remove(1));
            hasSplit = true;
        } else {
            throw new IllegalArgumentException("Cannot split. Conditions not met.");
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getSplitHand() {
        return splitHand;
    }

    public boolean hasSplit() {
        return hasSplit;
    }

    public void clearHand() {
        hand.clear();
        splitHand = null;
        hasSplit = false;
    }

    public void clearSplitHand() {
        splitHand = null;
        hasSplit = false;
    }

    public void placeBet(int amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Bet amount exceeds balance");
        }
        bet = amount;
        balance -= amount;
    }

    public void doubleDown() {
        if (bet * 2 > balance) {
            throw new IllegalArgumentException("Cannot double down, not enough balance");
        }
        balance -= bet;
        bet *= 2;
    }

    public void winBet() {
        balance += 2 * bet;
        bet = 0;
    }

    public void loseBet() {
        bet = 0;
    }

    public int getBalance() {
        return balance;
    }

    public int getHandValue() {
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
}
