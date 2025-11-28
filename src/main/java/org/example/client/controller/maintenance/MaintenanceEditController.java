package org.example.client.controller.maintenance;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.api.MaintenanceApi;
import org.example.client.model.Aircraft;
import org.example.client.model.Maintenance;
import org.example.client.util.ErrorDialog;

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
            ErrorDialog.show("Ошибка загрузки самолётов", e.getMessage());
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
                    typeField.getText().isBlank() ||
                    engineerField.getText().isBlank() ||
                    statusBox.getValue() == null) {

                ErrorDialog.show(
                        "Ошибка заполнения",
                        "Заполните все обязательные поля:\n" +
                                "— Самолёт\n— Дата ТО\n— Тип работ\n— Инженер\n— Статус"
                );
                return;
            }

            if (nextDueDate.getValue() != null &&
                    nextDueDate.getValue().isBefore(dateMaintenance.getValue())) {

                ErrorDialog.show(
                        "Ошибка дат",
                        "Дата следующего ТО не может быть раньше текущего ТО."
                );
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
            ErrorDialog.show("Ошибка сохранения", e.getMessage());
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
}
