package org.example.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.client.App;
import org.example.client.util.HttpClientUtil;
import org.example.client.util.ErrorDialog;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private static final ObjectMapper mapper = new ObjectMapper();

    @FXML
    public void onLogin() {
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();

        try {
            HttpClientUtil.setCredentials(user, pass);

            String json = HttpClientUtil.sendGet("http://localhost:8080/iss/api/auth/login");

            AuthResponse resp = mapper.readValue(json, AuthResponse.class);

            App.showMainWindow(resp.getRole());

        } catch (Exception e) {
            ErrorDialog.show("Ошибка входа", "Неверный логин или пароль.");
        }
    }

    public static class AuthResponse {
        private String username;
        private String role;

        public String getUsername() { return username; }
        public String getRole() { return role; }
    }
}
