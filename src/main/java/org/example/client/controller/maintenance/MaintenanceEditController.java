package org.example.client.controller.maintenance;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.api.MaintenanceApi;
import org.example.client.model.Aircraft;
import org.example.client.model.Maintenance;

public class MaintenanceEditController {

    @FXML private ComboBox<Aircraft> aircraftBox;
    @FXML private DatePicker dateMaintenance;
    @FXML private TextField typeField;
    @FXML private TextField engineerField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker nextDueDate;
    @FXML private ComboBox<String> statusBox;

    private Maintenance maintenance;
    private MaintenanceController parentController;

    public void setParent(MaintenanceController controller) {
        this.parentController = controller;
    }

    public void setMaintenance(Maintenance m) {
        this.maintenance = (m == null ? new Maintenance() : m);
        loadAircrafts();
        loadStatuses();
        fillFields();
    }

    private void loadAircrafts() {
        try {
            aircraftBox.getItems().setAll(AircraftApi.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStatuses() {
        statusBox.getItems().addAll("SCHEDULED", "IN_PROGRESS", "COMPLETED");
    }

    private void fillFields() {
        if (maintenance.getAircraft() != null)
            aircraftBox.setValue(maintenance.getAircraft());

        if (maintenance.getMaintenanceDate() != null)
            dateMaintenance.setValue(maintenance.getMaintenanceDate());

        typeField.setText(maintenance.getType());
        engineerField.setText(maintenance.getEngineerName());
        descriptionField.setText(maintenance.getDescription());
        nextDueDate.setValue(maintenance.getNextDueDate());
        statusBox.setValue(maintenance.getStatus());
    }

    @FXML
    private void onSave() {
        try {
            if (aircraftBox.getValue() == null ||
                    dateMaintenance.getValue() == null ||
                    typeField.getText().isEmpty() ||
                    engineerField.getText().isEmpty() ||
                    statusBox.getValue() == null) {

                showAlert("Ошибка", "Все обязательные поля должны быть заполнены");
                return;
            }

            maintenance.setAircraft(aircraftBox.getValue());
            maintenance.setMaintenanceDate(dateMaintenance.getValue());
            maintenance.setType(typeField.getText());
            maintenance.setEngineerName(engineerField.getText());
            maintenance.setDescription(descriptionField.getText());
            maintenance.setNextDueDate(nextDueDate.getValue());
            maintenance.setStatus(statusBox.getValue());

            if (maintenance.getId() == 0) {
                MaintenanceApi.save(maintenance);
            } else {
                MaintenanceApi.update(maintenance.getId(), maintenance);
            }

            parentController.refreshTable();
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) aircraftBox.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
