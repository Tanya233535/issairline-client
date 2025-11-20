package org.example.client.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController {

    @FXML private Label roleLabel;
    @FXML private Button flightsBtn;
    @FXML private Button passengersBtn;
    @FXML private Button crewBtn;
    @FXML private Button aircraftsBtn;
    @FXML private Button maintenanceBtn;

    public void setRole(String role) {
        roleLabel.setText("Ваша роль: " + role);

        switch (role) {
            case "VIEWER" -> {
                crewBtn.setDisable(true);
                passengersBtn.setDisable(true);
                maintenanceBtn.setDisable(true);
            }
            case "ENGINEER" -> {
                flightsBtn.setDisable(true);
                passengersBtn.setDisable(true);
                crewBtn.setDisable(true);
            }
        }
    }
}
