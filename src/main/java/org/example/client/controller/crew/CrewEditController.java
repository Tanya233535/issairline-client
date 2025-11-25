package org.example.client.controller.crew;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.example.client.api.CrewApi;
import org.example.client.api.FlightApi;
import org.example.client.model.CrewMember;
import org.example.client.model.Flight;

public class CrewEditController {

    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField roleField;
    @FXML private TextField qualificationField;
    @FXML private TextField experienceField;

    @FXML private ComboBox<Flight> flightBox;

    private CrewMember crewMember;
    private CrewController parent;

    public void setParent(CrewController p) {
        this.parent = p;
    }

    public void setCrewMember(CrewMember cm) {
        this.crewMember = (cm == null ? new CrewMember() : cm);

        loadFlights();
        fillFields();
    }

    private void loadFlights() {
        try {
            flightBox.getItems().setAll(FlightApi.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillFields() {
        if (crewMember.getLastName() != null) lastNameField.setText(crewMember.getLastName());
        if (crewMember.getFirstName() != null) firstNameField.setText(crewMember.getFirstName());
        if (crewMember.getMiddleName() != null) middleNameField.setText(crewMember.getMiddleName());
        if (crewMember.getRole() != null) roleField.setText(crewMember.getRole());
        if (crewMember.getQualification() != null) qualificationField.setText(crewMember.getQualification());
        if (crewMember.getExperienceYears() != 0) experienceField.setText(String.valueOf(crewMember.getExperienceYears()));
        if (crewMember.getFlight() != null) flightBox.setValue(crewMember.getFlight());
    }

    @FXML
    private void onSave() {

        try {
            if (lastNameField.getText().isEmpty() ||
                    firstNameField.getText().isEmpty() ||
                    roleField.getText().isEmpty()) {

                showError("Ошибка", "Фамилия, имя и роль обязательны!");
                return;
            }

            crewMember.setLastName(lastNameField.getText());
            crewMember.setFirstName(firstNameField.getText());
            crewMember.setMiddleName(middleNameField.getText());
            crewMember.setRole(roleField.getText());
            crewMember.setQualification(qualificationField.getText());

            int exp = 0;
            if (!experienceField.getText().isEmpty())
                exp = Integer.parseInt(experienceField.getText());
            crewMember.setExperienceYears(exp);

            crewMember.setFlight(flightBox.getValue());

            if (crewMember.getMemberId() == 0) {
                CrewApi.save(crewMember);
            } else {
                CrewApi.update(crewMember.getMemberId(), crewMember);
            }

            parent.refreshTable();
            close();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", e.getMessage());
        }
    }

    @FXML
    private void onCancel() {
        close();
    }

    private void close() {
        Stage stage = (Stage) lastNameField.getScene().getWindow();
        stage.close();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
