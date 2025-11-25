package org.example.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;
import org.example.client.App;

public class MainController {

    @FXML private Label roleLabel;
    @FXML private StackPane contentArea;

    @FXML private Button btnFlights;
    @FXML private Button btnPassengers;
    @FXML private Button btnCrew;
    @FXML private Button btnAircrafts;
    @FXML private Button btnMaintenance;
    @FXML private Button btnLogout;

    private String role;

    public void setRole(String role) {
        this.role = role;
        roleLabel.setText("Роль: " + role);
        applyRoleRestrictions();
    }

    private void applyRoleRestrictions() {
        switch (role) {
            case "VIEWER" -> {
                btnCrew.setDisable(true);
                btnPassengers.setDisable(true);
                btnMaintenance.setDisable(true);
            }
            case "ENGINEER" -> {
                btnFlights.setDisable(true);
                btnPassengers.setDisable(true);
                btnCrew.setDisable(true);
            }
        }
    }

    @FXML
    private void initialize() {
        btnAircrafts.setOnAction(e -> loadView("aircraft/AircraftView.fxml"));
        btnFlights.setOnAction(e -> loadView("flight/FlightView.fxml"));
        btnPassengers.setOnAction(e -> loadView("passenger/PassengerView.fxml"));
        btnCrew.setOnAction(e -> loadView("crew/CrewView.fxml"));
        btnMaintenance.setOnAction(e -> loadView("maintenance/MaintenanceView.fxml"));
        btnLogout.setOnAction(e -> onLogout());
    }

    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/" + fxmlPath));
            Parent view = loader.load();
            contentArea.getChildren().setAll(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/auth/login.fxml"));
            Parent loginRoot = loader.load();

            javafx.stage.Stage stage =
                    (javafx.stage.Stage) btnLogout.getScene().getWindow();

            stage.getScene().setRoot(loginRoot);
            stage.centerOnScreen();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
