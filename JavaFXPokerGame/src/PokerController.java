import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class PokerController {

    @FXML
    private TextArea playerArea;

    @FXML
    private TextArea computerArea;

    @FXML
    private Label resultLabel;

    @FXML
    private Button dealButton;

    private Deck deck;
    private Player player;
    private Player computer;

    @FXML
    public void initialize() {

        player = new Player("Player");
        computer = new Player("Computer");
    }

    @FXML
    private void dealCards() {

        player.clearHand();
        computer.clearHand();

        playerArea.clear();
        computerArea.clear();

        deck = new Deck();

        for (int i = 0; i < 5; i++) {
            player.addCard(deck.dealCard());
            computer.addCard(deck.dealCard());
        }

        for (Card c : player.getHand()) {
            playerArea.appendText(c.toString() + "\n");
        }

        for (Card c : computer.getHand()) {
            computerArea.appendText(c.toString() + "\n");
        }

        determineWinner();
    }

    private void determineWinner() {

        int playerHigh = player.getHighestCardValue();
        int computerHigh = computer.getHighestCardValue();

        if (playerHigh > computerHigh) {
            resultLabel.setText("Player Wins!");
        }
        else if (computerHigh > playerHigh) {
            resultLabel.setText("Computer Wins!");
        }
        else {
            resultLabel.setText("Draw!");
        }
    }
}
