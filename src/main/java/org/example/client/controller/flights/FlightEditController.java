package org.example.client.controller.flights;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.api.FlightApi;
import org.example.client.model.Aircraft;
import org.example.client.model.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FlightEditController {

    @FXML private TextField flightNoField;

    @FXML private TextField departureAirportField;
    @FXML private TextField arrivalAirportField;

    @FXML private DatePicker dateDep;
    @FXML private TextField timeDep;

    @FXML private DatePicker dateArr;
    @FXML private TextField timeArr;

    @FXML private ComboBox<String> statusBox;
    @FXML private ComboBox<Aircraft> aircraftBox;

    private Flight flight;
    private FlightController parentController;

    public void setParent(FlightController controller) {
        this.parentController = controller;
    }

    public void setFlight(Flight f) {
        this.flight = (f == null ? new Flight() : f);
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
        statusBox.getItems().addAll(
                "SCHEDULED", "ON_TIME", "DELAYED", "CANCELLED", "DEPARTED", "ARRIVED"
        );
    }

    private void fillFields() {
        if (flight.getFlightNo() != null) {
            flightNoField.setText(flight.getFlightNo());
        }

        if (flight.getDepartureAirport() != null) {
            departureAirportField.setText(flight.getDepartureAirport());
        }

        if (flight.getArrivalAirport() != null) {
            arrivalAirportField.setText(flight.getArrivalAirport());
        }

        if (flight.getScheduledDeparture() != null) {
            dateDep.setValue(flight.getScheduledDeparture().toLocalDate());
            timeDep.setText(flight.getScheduledDeparture().toLocalTime().toString());
        }

        if (flight.getScheduledArrival() != null) {
            dateArr.setValue(flight.getScheduledArrival().toLocalDate());
            timeArr.setText(flight.getScheduledArrival().toLocalTime().toString());
        }

        if (flight.getStatus() != null) {
            statusBox.setValue(flight.getStatus());
        }

        if (flight.getAircraft() != null) {
            aircraftBox.setValue(flight.getAircraft());
        }
    }

    @FXML
    private void onSave() {
        try {

            if (flightNoField.getText().isEmpty() ||
                    departureAirportField.getText().isEmpty() ||
                    arrivalAirportField.getText().isEmpty() ||
                    dateDep.getValue() == null ||
                    dateArr.getValue() == null ||
                    timeDep.getText().isEmpty() ||
                    timeArr.getText().isEmpty() ||
                    statusBox.getValue() == null ||
                    aircraftBox.getValue() == null) {

                showAlert("Ошибка", "Все поля должны быть заполнены");
                return;
            }

            LocalTime depTime = LocalTime.parse(timeDep.getText());
            LocalTime arrTime = LocalTime.parse(timeArr.getText());

            LocalDateTime depDT = LocalDateTime.of(dateDep.getValue(), depTime);
            LocalDateTime arrDT = LocalDateTime.of(dateArr.getValue(), arrTime);

            if (arrDT.isBefore(depDT)) {
                showAlert("Ошибка", "Прибытие не может быть раньше вылета");
                return;
            }


            flight.setFlightNo(flightNoField.getText());
            flight.setDepartureAirport(departureAirportField.getText());
            flight.setArrivalAirport(arrivalAirportField.getText());
            flight.setScheduledDeparture(depDT);
            flight.setScheduledArrival(arrDT);
            flight.setStatus(statusBox.getValue());
            flight.setAircraft(aircraftBox.getValue());


            if (flight.getId() == 0) {
                FlightApi.save(flight);
            } else {
                FlightApi.update(flight.getId(), flight);
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
        Stage stage = (Stage) flightNoField.getScene().getWindow();
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
