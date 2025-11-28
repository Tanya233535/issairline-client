package org.example.client.controller.crew;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.client.api.CrewApi;
import org.example.client.api.FlightApi;
import org.example.client.model.CrewMember;
import org.example.client.model.Flight;
import org.example.client.util.ErrorDialog;
import org.example.client.util.Validator;

public class CrewEditController {

    @FXML private Label titleLabel;
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField roleField;
    @FXML private TextField qualificationField;
    @FXML private TextField experienceField;
    @FXML private ComboBox<Flight> flightBox;

    private CrewMember crewMember;
    private CrewController parent;

    @FXML
    public void initialize() {
        try {
            flightBox.getItems().setAll(FlightApi.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setParent(CrewController parent) {
        this.parent = parent;
    }

    public void setCrew(CrewMember member) {

        this.crewMember = (member == null ? new CrewMember() : member);

        titleLabel.setText(member == null
                ? "Добавить сотрудника"
                : "Редактировать сотрудника");

        if (member != null) {

            if (member.getLastName() != null)
                lastNameField.setText(member.getLastName());

            if (member.getFirstName() != null)
                firstNameField.setText(member.getFirstName());

            if (member.getMiddleName() != null)
                middleNameField.setText(member.getMiddleName());

            if (member.getRole() != null)
                roleField.setText(member.getRole());

            if (member.getQualification() != null)
                qualificationField.setText(member.getQualification());

            if (member.getExperienceYears() != 0)
                experienceField.setText(String.valueOf(member.getExperienceYears()));

            if (member.getFlight() != null)
                flightBox.setValue(member.getFlight());
        }
    }

    @FXML
    private void onSave() {
        try {
            var errors = Validator.collector();

            Validator.require(lastNameField, "Фамилия", errors);
            Validator.require(firstNameField, "Имя", errors);
            Validator.require(roleField, "Роль", errors);
            Validator.nonNegativeInt(experienceField, "Стаж", errors);

            if (!errors.isEmpty()) {
                ErrorDialog.show("Ошибка ввода данных", errors);
                return;
            }

            crewMember.setLastName(lastNameField.getText());
            crewMember.setFirstName(firstNameField.getText());
            crewMember.setMiddleName(middleNameField.getText());
            crewMember.setRole(roleField.getText());
            crewMember.setQualification(qualificationField.getText());
            crewMember.setExperienceYears(Integer.parseInt(experienceField.getText()));
            crewMember.setFlight(flightBox.getValue());

            if (crewMember.getMemberId() == 0)
                CrewApi.save(crewMember);
            else
                CrewApi.update(crewMember.getMemberId(), crewMember);

            parent.refreshTable();
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
