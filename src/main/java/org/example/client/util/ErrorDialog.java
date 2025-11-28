package org.example.client.util;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

public class ErrorDialog {

    public static void show(String title, List<String> messages) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(String.join("\n", messages));
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(200);
        textArea.setPrefWidth(380);

        VBox box = new VBox(textArea);
        alert.getDialogPane().setContent(box);

        alert.showAndWait();
    }

    public static void show(String title, String message) {
        show(title, List.of(message));
    }
}
