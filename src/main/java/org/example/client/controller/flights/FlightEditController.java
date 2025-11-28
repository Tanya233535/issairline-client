package org.example.client.controller.flights;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.AircraftApi;
import org.example.client.api.FlightApi;
import org.example.client.model.Aircraft;
import org.example.client.model.Flight;
import org.example.client.util.ErrorDialog;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class FlightEditController {

    @FXML private Label titleLabel;

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

        if (f == null) {
            titleLabel.setText("Добавить рейс");
        } else {
            titleLabel.setText("Редактировать рейс");
            fillFields();
        }
    }

    private void loadAircrafts() {
        try {
            aircraftBox.getItems().setAll(AircraftApi.getAll());
        } catch (Exception e) {
            ErrorDialog.show("Ошибка загрузки самолётов", e.getMessage());
        }
    }

    private void loadStatuses() {
        statusBox.getItems().addAll(
                "SCHEDULED", "ON_TIME", "DELAYED", "CANCELLED", "DEPARTED", "ARRIVED"
        );
    }

    private void fillFields() {
        flightNoField.setText(flight.getFlightNo());
        departureAirportField.setText(flight.getDepartureAirport());
        arrivalAirportField.setText(flight.getArrivalAirport());

        dateDep.setValue(flight.getScheduledDeparture().toLocalDate());
        timeDep.setText(flight.getScheduledDeparture().toLocalTime().toString());

        dateArr.setValue(flight.getScheduledArrival().toLocalDate());
        timeArr.setText(flight.getScheduledArrival().toLocalTime().toString());

        statusBox.setValue(flight.getStatus());
        aircraftBox.setValue(flight.getAircraft());
    }

    @FXML
    private void onSave() {
        try {
            if (flightNoField.getText().isBlank()
                    || departureAirportField.getText().isBlank()
                    || arrivalAirportField.getText().isBlank()
                    || dateDep.getValue() == null
                    || dateArr.getValue() == null
                    || timeDep.getText().isBlank()
                    || timeArr.getText().isBlank()
                    || statusBox.getValue() == null
                    || aircraftBox.getValue() == null) {

                ErrorDialog.show("Ошибка заполнения",
                        "Заполните все обязательные поля.");
                return;
            }

            LocalTime depTime;
            LocalTime arrTime;

            try {
                depTime = LocalTime.parse(timeDep.getText());
                arrTime = LocalTime.parse(timeArr.getText());
            } catch (Exception ex) {
                ErrorDialog.show("Ошибка формата времени",
                        "Введите время в формате HH:mm");
                return;
            }

            LocalDateTime depDT = LocalDateTime.of(dateDep.getValue(), depTime);
            LocalDateTime arrDT = LocalDateTime.of(dateArr.getValue(), arrTime);

            if (arrDT.isBefore(depDT)) {
                ErrorDialog.show("Ошибка дат",
                        "Прибытие не может быть раньше вылета.");
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
            ErrorDialog.show("Ошибка сохранения", e.getMessage());
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
}
