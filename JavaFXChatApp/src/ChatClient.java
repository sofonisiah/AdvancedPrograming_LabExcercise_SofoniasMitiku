import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ChatClient extends Application {

    private TextArea chatArea;
    private TextField messageField;
    private TextField usernameField;

    private PrintWriter writer;
    private BufferedReader reader;

    @Override
    public void start(Stage stage) {

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setWrapText(true);

        usernameField = new TextField();
        usernameField.setPromptText("Enter username");

        messageField = new TextField();
        messageField.setPromptText("Type message");

        Button sendButton = new Button("Send");

        sendButton.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, messageField, sendButton);
        VBox root = new VBox(10,
                new Label("Username:"),
                usernameField,
                chatArea,
                inputBox
        );

        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 500, 400);

        stage.setTitle("JavaFX Chat App");
        stage.setScene(scene);
        stage.show();

        connectToServer();
    }

    private void connectToServer() {

        try {

            Socket socket = new Socket("localhost", 5000);

            writer = new PrintWriter(socket.getOutputStream(), true);

            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            new Thread(() -> {

                try {

                    String message;

                    while ((message = reader.readLine()) != null) {

                        String finalMessage = message;

                        Platform.runLater(() ->
                                chatArea.appendText(finalMessage + "\n")
                        );
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {

        String username = usernameField.getText().trim();
        String message = messageField.getText().trim();

        if (!username.isEmpty() && !message.isEmpty()) {

            writer.println(username + ": " + message);

            messageField.clear();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
