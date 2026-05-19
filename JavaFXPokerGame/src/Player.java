import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHighestCardValue() {

        int max = 0;

        for (Card c : hand) {
            if (c.getValue() > max) {
                max = c.getValue();
            }
        }

        return max;
    }

    public void clearHand() {
        hand.clear();
    }
}
