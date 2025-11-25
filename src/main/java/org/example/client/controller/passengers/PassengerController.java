package org.example.client.controller.passengers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.App;
import org.example.client.api.PassengerApi;
import org.example.client.model.Passenger;

import java.util.List;
import java.util.stream.Collectors;

public class PassengerController {

    @FXML private TableView<Passenger> table;
    @FXML private TableColumn<Passenger,String> colLastName;
    @FXML private TableColumn<Passenger,String> colFirstName;
    @FXML private TableColumn<Passenger,String> colTicket;
    @FXML private TableColumn<Passenger,String> colPassport;
    @FXML private TableColumn<Passenger,String> colSeat;
    @FXML private TableColumn<Passenger,String> colFlight;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<Passenger> fullList;

    @FXML
    private void initialize() {
        colLastName.setCellValueFactory(c -> c.getValue().lastNameProperty());
        colFirstName.setCellValueFactory(c -> c.getValue().firstNameProperty());
        colTicket.setCellValueFactory(c -> c.getValue().ticketNumberProperty());
        colPassport.setCellValueFactory(c -> c.getValue().passportNumberProperty());
        colSeat.setCellValueFactory(c -> c.getValue().seatProperty());
        colFlight.setCellValueFactory(c -> {
            if (c.getValue().getFlight() == null) return new javafx.beans.property.SimpleStringProperty("-");
            return c.getValue().getFlight().flightNoProperty();
        });

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());

        loadData();
    }

    private void loadData() {
        try {
            fullList = PassengerApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось загрузить пассажиров: " + e.getMessage());
        }
    }

    private void search() {
        String q = searchField.getText().toLowerCase();
        if (q.isBlank()) {
            table.getItems().setAll(fullList);
            return;
        }

        List<Passenger> filtered = fullList.stream()
                .filter(p -> p.getLastName().toLowerCase().contains(q)
                        || p.getTicketNumber().toLowerCase().contains(q))
                .collect(Collectors.toList());
        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        Passenger p = table.getSelectionModel().getSelectedItem();
        if (p == null) return;
        try {
            PassengerApi.delete(p.getId());
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось удалить: " + e.getMessage());
        }
    }

    private void openEditWindow(Passenger passenger) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/passenger/PassengerEditDialog.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(passenger == null ? "Добавить пассажира" : "Редактировать пассажира");
            stage.initModality(Modality.APPLICATION_MODAL);

            PassengerEditController controller = loader.getController();
            controller.setParent(this);
            controller.setPassenger(passenger);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Ошибка", "Не удалось открыть окно редактирования: " + e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
