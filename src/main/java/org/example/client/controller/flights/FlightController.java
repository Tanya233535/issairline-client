package org.example.client.controller.flights;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.App;
import org.example.client.api.FlightApi;
import org.example.client.model.Flight;
import org.example.client.util.ErrorDialog;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class FlightController {

    @FXML private TableView<Flight> table;
    @FXML private TableColumn<Flight,String> colFlightNo;
    @FXML private TableColumn<Flight,String> colDepartureAirport;
    @FXML private TableColumn<Flight,String> colArrivalAirport;
    @FXML private TableColumn<Flight,String> colStatus;
    @FXML private TableColumn<Flight,String> colAircraft;
    @FXML private TableColumn<Flight,String> colDeparture;
    @FXML private TableColumn<Flight,String> colArrival;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<Flight> fullList;
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @FXML
    private void initialize() {

        colFlightNo.setCellValueFactory(c -> c.getValue().flightNoProperty());
        colDepartureAirport.setCellValueFactory(c -> c.getValue().departureAirportProperty());
        colArrivalAirport.setCellValueFactory(c -> c.getValue().arrivalAirportProperty());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());

        colAircraft.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getAircraft() != null ?
                                c.getValue().getAircraft().getModel() : "-"
                )
        );

        colDeparture.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getScheduledDeparture().format(fmt)
                ));

        colArrival.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getScheduledArrival().format(fmt)
                ));

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());

        loadData();
    }

    private void loadData() {
        try {
            fullList = FlightApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            ErrorDialog.show("Ошибка загрузки рейсов", e.getMessage());
        }
    }

    private void search() {
        String q = searchField.getText().toLowerCase();

        List<Flight> filtered = fullList.stream()
                .filter(f -> f.getFlightNo().toLowerCase().contains(q)
                        || f.getDepartureAirport().toLowerCase().contains(q)
                        || f.getArrivalAirport().toLowerCase().contains(q))
                .collect(Collectors.toList());

        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        Flight f = table.getSelectionModel().getSelectedItem();
        if (f == null) return;

        try {
            FlightApi.delete(f.getId());
            loadData();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка удаления", e.getMessage());
        }
    }

    private void openEditWindow(Flight flight) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/flight/FlightEditDialog.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));
            stage.setTitle(flight == null ? "Добавить рейс" : "Редактировать рейс");
            stage.initModality(Modality.APPLICATION_MODAL);

            FlightEditController controller = loader.getController();
            controller.setParent(this);
            controller.setFlight(flight);

            stage.show();

        } catch (Exception e) {
            ErrorDialog.show("Ошибка открытия окна", e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }
}
