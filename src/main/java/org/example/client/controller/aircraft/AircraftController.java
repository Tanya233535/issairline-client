package org.example.client.controller.aircraft;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.client.App;
import org.example.client.api.AircraftApi;
import org.example.client.model.Aircraft;
import org.example.client.util.ErrorDialog;

import java.util.List;
import java.util.stream.Collectors;

public class AircraftController {

    @FXML private TableView<Aircraft> table;
    @FXML private TableColumn<Aircraft, String> colCode;
    @FXML private TableColumn<Aircraft, String> colModel;
    @FXML private TableColumn<Aircraft, Integer> colYear;
    @FXML private TableColumn<Aircraft, Integer> colCapacity;
    @FXML private TableColumn<Aircraft, String> colStatus;
    @FXML private TableColumn<Aircraft, String> colHours;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<Aircraft> fullList;

    @FXML
    private void initialize() {
        colCode.setCellValueFactory(c -> c.getValue().aircraftCodeProperty());
        colModel.setCellValueFactory(c -> c.getValue().modelProperty());
        colYear.setCellValueFactory(c -> c.getValue().manufactureYearProperty().asObject());
        colCapacity.setCellValueFactory(c -> c.getValue().capacityProperty().asObject());
        colStatus.setCellValueFactory(c -> c.getValue().statusProperty());
        colHours.setCellValueFactory(c -> c.getValue().formattedHoursProperty());

        loadData();

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());
    }

    private void loadData() {
        try {
            fullList = AircraftApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            ErrorDialog.show("Ошибка загрузки данных", e.getMessage());
        }
    }

    private void search() {
        String q = searchField.getText().toLowerCase();

        List<Aircraft> filtered = fullList.stream()
                .filter(a -> a.getModel().toLowerCase().contains(q)
                        || a.getAircraftCode().toLowerCase().contains(q))
                .collect(Collectors.toList());
        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        Aircraft a = table.getSelectionModel().getSelectedItem();
        if (a == null) return;

        try {
            AircraftApi.delete(a.getAircraftCode());
            loadData();
        } catch (Exception e) {
            ErrorDialog.show("Ошибка удаления", e.getMessage());
        }
    }

    private void openEditWindow(Aircraft aircraft) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/aircraft/AircraftEditDialog.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));
            stage.setTitle(aircraft == null ? "Добавить самолёт" : "Редактировать самолёт");
            stage.initModality(Modality.APPLICATION_MODAL);

            AircraftEditController controller = loader.getController();
            controller.setAircraft(aircraft);
            controller.setParent(this);

            stage.show();

        } catch (Exception e) {
            ErrorDialog.show("Ошибка открытия окна", e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }
}
