package org.example.client.controller.users;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.UserApi;
import org.example.client.model.User;
import org.example.client.util.ErrorDialog;
import org.example.client.util.Validator;

import java.util.List;

public class UserEditController {

    @FXML private Label titleLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleBox;
    @FXML private Label infoLabel;

    private User user;
    private org.example.client.controller.users.UserController parent;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll("ADMIN", "ENGINEER", "DISPATCHER", "VIEWER");
        infoLabel.setText("");
    }

    public void setParent(org.example.client.controller.users.UserController parent) {
        this.parent = parent;
    }

    public void setUser(User u) {
        this.user = (u == null ? new User() : u);

        if (u == null) {
            titleLabel.setText("Добавить пользователя");
            usernameField.setText("");
            passwordField.setText("");
            roleBox.setValue("VIEWER");
            infoLabel.setText("Пароль обязателен при создании");
        } else {
            titleLabel.setText("Редактировать пользователя");
            usernameField.setText(u.getUsername());
            passwordField.setText("");
            roleBox.setValue(u.getRole());
            infoLabel.setText("Оставьте пароль пустым, чтобы не менять его");
        }
    }

    @FXML
    private void onSave() {
        try {
            var errors = Validator.collector();

            Validator.require(usernameField, "Логин", errors);
            Validator.requireCombo(roleBox, "Роль", errors);

            // пароль: при создании обязателен, при редактировании опционален
            boolean creating = (user.getId() == 0);
            String pw = passwordField.getText();

            if (creating) {
                if (pw == null || pw.isBlank()) {
                    errors.add("Пароль — обязательное поле при создании пользователя.");
                } else if (pw.length() < 3) {
                    errors.add("Пароль должен быть не короче 3 символов.");
                }
            } else {
                if (pw != null && !pw.isBlank() && pw.length() < 3) {
                    errors.add("Пароль должен быть не короче 3 символов.");
                }
            }

            if (!errors.isEmpty()) {
                ErrorDialog.show("Ошибка ввода данных", errors);
                return;
            }

            user.setUsername(usernameField.getText().trim());
            if (pw != null && !pw.isBlank()) {
                user.setPassword(pw);
            } else {
                user.setPassword(null);
            }
            user.setRole(roleBox.getValue());

            if (creating) {
                UserApi.create(user);
            } else {
                UserApi.update(user.getId(), user);
            }

            if (parent != null) parent.refreshTable();
            close();

        } catch (Exception e) {
            ErrorDialog.show("Ошибка сохранения", e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
