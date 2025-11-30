package org.example.client.controller.maintenance;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.App;
import org.example.client.api.MaintenanceApi;
import org.example.client.model.Maintenance;
import org.example.client.security.RoleAccessManager;
import org.example.client.util.ErrorDialog;

import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceController {

    @FXML private TableView<Maintenance> table;
    @FXML private TableColumn<Maintenance, String> colAircraft;
    @FXML private TableColumn<Maintenance, String> colDate;
    @FXML private TableColumn<Maintenance, String> colType;
    @FXML private TableColumn<Maintenance, String> colEngineer;
    @FXML private TableColumn<Maintenance, String> colStatus;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<Maintenance> fullList;

    @FXML
    private void initialize() {

        colAircraft.setCellValueFactory(c ->
                c.getValue().aircraftProperty().get().modelProperty());

        colDate.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getMaintenanceDate().toString()
                ));

        colType.setCellValueFactory(c -> c.getValue().typeProperty());
        colEngineer.setCellValueFactory(c -> c.getValue().engineerNameProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());

        loadData();
    }

    private void loadData() {
        try {
            fullList = MaintenanceApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            ErrorDialog.show("Ошибка загрузки ТО", e.getMessage());
        }
    }

    private String role;

    public void setRole(String role) {
        this.role = role;
        applyRoleRestrictions();
    }

    private void applyRoleRestrictions() {

        btnAdd.setDisable(!RoleAccessManager.canEditMaintenance(role));
        btnEdit.setDisable(!RoleAccessManager.canEditMaintenance(role));
        btnDelete.setDisable(!RoleAccessManager.canEditMaintenance(role));
    }

    private void search() {
        String q = searchField.getText().toLowerCase();

        List<Maintenance> filtered = fullList.stream()
                .filter(m ->
                        m.getType().toLowerCase().contains(q) ||
                                m.getEngineerName().toLowerCase().contains(q) ||
                                m.getAircraft().getModel().toLowerCase().contains(q))
                .collect(Collectors.toList());

        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        Maintenance m = table.getSelectionModel().getSelectedItem();
        if (m == null) return;

        try {
            MaintenanceApi.delete(m.getId());
            loadData();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка удаления", e.getMessage());
        }
    }

    private void openEditWindow(Maintenance m) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/maintenance/MaintenanceEditDialog.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));
            stage.setTitle(m == null ? "Добавить ТО" : "Редактировать ТО");
            stage.initModality(Modality.APPLICATION_MODAL);

            MaintenanceEditController controller = loader.getController();
            controller.setParent(this);
            controller.setMaintenance(m);

            stage.show();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка открытия окна", e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }
}
