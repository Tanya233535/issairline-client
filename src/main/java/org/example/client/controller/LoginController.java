package org.example.client.controller;

import org.example.client.App;
//import org.example.client.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void onLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals("admin") && pass.equals("admin")) {
            openMainWindow("ADMIN");
        } else if (user.equals("dispatcher") && pass.equals("123")) {
            openMainWindow("DISPATCHER");
        } else if (user.equals("viewer") && pass.equals("000")) {
            openMainWindow("VIEWER");
        } else {
            errorLabel.setText("Неверный логин или пароль!");
        }
    }

    private void openMainWindow(String role) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/main.fxml"));
            Scene scene = new Scene(loader.load());

            MainController controller = loader.getController();
            controller.setRole(role);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("ИСАА — Главная");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
