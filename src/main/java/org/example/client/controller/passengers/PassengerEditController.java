package org.example.client.controller.passengers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.FlightApi;
import org.example.client.api.PassengerApi;
import org.example.client.model.Flight;
import org.example.client.model.Passenger;

import java.util.List;

public class PassengerEditController {

    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField passportField;
    @FXML private TextField ticketField;
    @FXML private TextField seatField;
    @FXML private ComboBox<Flight> flightBox;

    private Passenger passenger;
    private PassengerController parent;

    public void setParent(PassengerController p) { this.parent = p; }

    public void setPassenger(Passenger p) {
        this.passenger = (p == null ? new Passenger() : p);
        loadFlights();
        fillFields();
    }

    private void loadFlights() {
        try {
            List<Flight> flights = FlightApi.getAll();
            flightBox.getItems().setAll(flights);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillFields() {
        if (passenger == null) return;
        if (passenger.getLastName() != null) lastNameField.setText(passenger.getLastName());
        if (passenger.getFirstName() != null) firstNameField.setText(passenger.getFirstName());
        if (passenger.getMiddleName() != null) middleNameField.setText(passenger.getMiddleName());
        if (passenger.getPassportNumber() != null) passportField.setText(passenger.getPassportNumber());
        if (passenger.getTicketNumber() != null) ticketField.setText(passenger.getTicketNumber());
        if (passenger.getSeat() != null) seatField.setText(passenger.getSeat());
        if (passenger.getFlight() != null) flightBox.setValue(passenger.getFlight());
    }

    @FXML
    private void onSave() {
        try {
            // простая валидация
            if (lastNameField.getText().isBlank() || firstNameField.getText().isBlank()
                    || passportField.getText().isBlank() || ticketField.getText().isBlank()
                    || seatField.getText().isBlank()) {
                showAlert("Ошибка", "Заполните обязательные поля");
                return;
            }

            passenger.setLastName(lastNameField.getText());
            passenger.setFirstName(firstNameField.getText());
            passenger.setMiddleName(middleNameField.getText());
            passenger.setPassportNumber(passportField.getText());
            passenger.setTicketNumber(ticketField.getText());
            passenger.setSeat(seatField.getText());
            passenger.setFlight(flightBox.getValue());

            if (passenger.getId() == 0) {
                PassengerApi.create(passenger);
            } else {
                PassengerApi.update(passenger.getId(), passenger);
            }

            parent.refreshTable();
            closeWindow();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", e.getMessage());
        }
    }

    @FXML private void onCancel() { closeWindow(); }

    private void closeWindow() {
        Stage st = (Stage) lastNameField.getScene().getWindow();
        st.close();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
