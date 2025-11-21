package org.example.client.controller.aircraft;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.model.Aircraft;

public class AircraftEditController {

    @FXML private TextField aircraftCodeField;
    @FXML private TextField modelField;
    @FXML private TextField yearField;
    @FXML private TextField capacityField;
    @FXML private ComboBox<String> statusBox;
    @FXML private Label errorLabel;

    private Aircraft aircraft;
    private AircraftController parentController; // <<< добавили

    private final AircraftApi api = new AircraftApi();

    @FXML
    public void initialize() {
        statusBox.getItems().addAll("ACTIVE", "MAINTENANCE", "RETIRED");
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;

        if (aircraft != null) {
            aircraftCodeField.setText(aircraft.getAircraftCode());
            aircraftCodeField.setDisable(true);

            modelField.setText(aircraft.getModel());
            yearField.setText(String.valueOf(aircraft.getManufactureYear()));
            capacityField.setText(String.valueOf(aircraft.getCapacity()));
            statusBox.setValue(aircraft.getStatus());
        }
    }

    // <<< вот этого не хватало
    public void setParent(AircraftController parent) {
        this.parentController = parent;
    }

    @FXML
    private void onSave() {
        try {
            errorLabel.setText("");

            if (aircraft == null) {
                aircraft = new Aircraft();
            }

            aircraft.setAircraftCode(aircraftCodeField.getText());
            aircraft.setModel(modelField.getText());
            aircraft.setManufactureYear(Integer.parseInt(yearField.getText()));
            aircraft.setCapacity(Integer.parseInt(capacityField.getText()));
            aircraft.setStatus(statusBox.getValue());

            if (aircraftCodeField.isDisable()) {
                api.update(aircraft);
            } else {
                api.create(aircraft);
            }


            if (parentController != null) {
                parentController.refreshTable();
            }

            close();

        } catch (Exception e) {
            errorLabel.setText("Ошибка: " + e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }
}
