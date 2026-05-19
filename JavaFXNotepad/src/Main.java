import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Main extends Application {

    private TextArea textArea;

    @Override
    public void start(Stage stage) {

        textArea = new TextArea();

        Button newButton = new Button("New");
        Button openButton = new Button("Open");
        Button saveButton = new Button("Save");

        newButton.setOnAction(e -> newFile());
        openButton.setOnAction(e -> openFile(stage));
        saveButton.setOnAction(e -> saveFile(stage));

        HBox toolbar = new HBox(10, newButton, openButton, saveButton);
        toolbar.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(toolbar);
        root.setCenter(textArea);

        Scene scene = new Scene(root, 700, 500);

        stage.setTitle("JavaFX Notepad");
        stage.setScene(scene);
        stage.show();
    }

    private void newFile() {
        textArea.clear();
    }

    private void openFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Text File");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            try {

                BufferedReader reader = new BufferedReader(
                        new FileReader(file)
                );

                textArea.clear();

                String line;

                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }

                reader.close();

            } catch (IOException e) {
                showError("Could not open file.");
            }
        }
    }

    private void saveFile(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Text File");

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {

            try {

                BufferedWriter writer = new BufferedWriter(
                        new FileWriter(file)
                );

                writer.write(textArea.getText());

                writer.close();

            } catch (IOException e) {
                showError("Could not save file.");
            }
        }
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
