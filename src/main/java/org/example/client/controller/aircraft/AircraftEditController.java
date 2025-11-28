package org.example.client.controller.aircraft;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.model.Aircraft;
import javafx.scene.control.Label;
import org.example.client.util.ErrorDialog;
import org.example.client.util.Validator;

import java.time.LocalDate;

public class AircraftEditController {

    @FXML private Label titleLabel;
    @FXML private TextField aircraftCodeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private TextField capacityField;
    @FXML private ComboBox<String> statusBox;
    @FXML private TextField hoursField;
    @FXML private DatePicker maintenanceDateField;

    private Aircraft aircraft;
    private AircraftController parentController;

    @FXML
    public void initialize() {
        statusBox.getItems().addAll("ACTIVE", "MAINTENANCE", "RETIRED");
    }

    public void setParent(AircraftController parent) {
        this.parentController = parent;
    }

    public void setAircraft(Aircraft aircraft) {

        this.aircraft = (aircraft == null ? new Aircraft() : aircraft);

        if (aircraft != null) {
            titleLabel.setText("Редактировать самолёт");

            aircraftCodeField.setText(aircraft.getAircraftCode());
            aircraftCodeField.setDisable(true);

            modelField.setText(aircraft.getModel());
            yearField.setText(String.valueOf(aircraft.getManufactureYear()));
            capacityField.setText(String.valueOf(aircraft.getCapacity()));
            statusBox.setValue(aircraft.getStatus());

            hoursField.setText(String.valueOf(aircraft.getTotalFlightHours()));

            maintenanceDateField.setValue(aircraft.getLastMaintenanceDate());
        } else {
            titleLabel.setText("Добавить самолёт");
        }
    }

    @FXML
    private void onSave() {
        try {
            var errors = Validator.collector();

            Validator.require(aircraftCodeField, "Код", errors);
            Validator.require(modelField, "Модель", errors);
            Validator.nonNegativeInt(yearField, "Год выпуска", errors);
            Validator.nonNegativeInt(capacityField, "Вместимость", errors);
            Validator.requireCombo(statusBox, "Статус", errors);
            Validator.nonNegativeDouble(hoursField, "Налёт", errors);
            Validator.requireDate(maintenanceDateField, "Дата последнего ТО", errors);
            Validator.dateNotFuture(maintenanceDateField, "Дата последнего ТО", errors);
            Validator.dateNotBefore(maintenanceDateField, LocalDate.of(1950,1,1),
                    "Дата последнего ТО", errors);

            if (!errors.isEmpty()) {
                ErrorDialog.show("Ошибка ввода данных", errors);
                return;
            }

            int year = Integer.parseInt(yearField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            double hours = hoursField.getText().isBlank()
                    ? 0
                    : Double.parseDouble(hoursField.getText());

            aircraft.setAircraftCode(aircraftCodeField.getText());
            aircraft.setModel(modelField.getText());
            aircraft.setManufactureYear(year);
            aircraft.setCapacity(capacity);
            aircraft.setStatus(statusBox.getValue());
            aircraft.setTotalFlightHours(hours);
            aircraft.setLastMaintenanceDate(maintenanceDateField.getValue());

            if (aircraftCodeField.isDisabled()) {
                AircraftApi.update(aircraft);
            } else {
                AircraftApi.create(aircraft);
            }

            if (parentController != null) parentController.refreshTable();
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
