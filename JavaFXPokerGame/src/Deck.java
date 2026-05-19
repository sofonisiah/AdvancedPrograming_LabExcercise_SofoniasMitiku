import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {

    private Stack<Card> deck;

    public Deck() {
        deck = new Stack<>();
        createDeck();
        shuffleDeck();
    }

    private void createDeck() {

        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        String[] ranks = {
                "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "J", "Q", "K", "A"
        };

        int value;

        ArrayList<Card> tempCards = new ArrayList<>();

        for (String suit : suits) {

            value = 2;

            for (String rank : ranks) {
                tempCards.add(new Card(suit, rank, value));
                value++;
            }
        }

        for (Card c : tempCards) {
            deck.push(c);
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Card dealCard() {

        if (!deck.isEmpty()) {
            return deck.pop();
        }

        return null;
    }
}
