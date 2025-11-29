package org.example.client.controller.users;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.App;
import org.example.client.api.UserApi;
import org.example.client.model.User;
import org.example.client.util.ErrorDialog;

import java.util.List;
import java.util.stream.Collectors;

public class UserController {

    @FXML private TableView<User> table;
    @FXML private TableColumn<User, Number> colId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colRole;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<User> fullList;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(c -> c.getValue().idProperty());
        colUsername.setCellValueFactory(c -> c.getValue().usernameProperty());
        colRole.setCellValueFactory(c -> c.getValue().roleProperty());

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());

        loadData();
    }

    private void loadData() {
        try {
            fullList = UserApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            ErrorDialog.show("Ошибка загрузки данных", e.getMessage());
        }
    }

    private void search() {
        String q = searchField.getText().toLowerCase().trim();
        if (q.isEmpty()) {
            table.getItems().setAll(fullList);
            return;
        }

        List<User> filtered = fullList.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(q)
                        || (u.getRole() != null && u.getRole().toLowerCase().contains(q)))
                .collect(Collectors.toList());
        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        User u = table.getSelectionModel().getSelectedItem();
        if (u == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Подтвердите удаление");
        confirm.setHeaderText(null);
        confirm.setContentText("Удалить пользователя " + u.getUsername() + " ?");
        var res = confirm.showAndWait();
        if (res.isEmpty() || res.get() != ButtonType.OK) return;

        try {
            UserApi.delete(u.getId());
            loadData();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка удаления", e.getMessage());
        }
    }

    private void openEditWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/users/UserEditDialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(user == null ? "Добавить пользователя" : "Редактировать пользователя");
            stage.initModality(Modality.APPLICATION_MODAL);

            UserEditController controller = loader.getController();
            controller.setParent(this);
            controller.setUser(user);

            stage.show();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка открытия окна", e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }
}
