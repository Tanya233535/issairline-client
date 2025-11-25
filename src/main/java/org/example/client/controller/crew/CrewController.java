package org.example.client.controller.crew;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.example.client.App;
import org.example.client.api.CrewApi;
import org.example.client.model.CrewMember;

import java.util.List;
import java.util.stream.Collectors;

public class CrewController {

    @FXML private TableView<CrewMember> table;
    @FXML private TableColumn<CrewMember, String> colLast;
    @FXML private TableColumn<CrewMember, String> colFirst;
    @FXML private TableColumn<CrewMember, String> colMiddle;
    @FXML private TableColumn<CrewMember, String> colRole;
    @FXML private TableColumn<CrewMember, String> colQual;
    @FXML private TableColumn<CrewMember, Number> colExp;
    @FXML private TableColumn<CrewMember, String> colFlight;

    @FXML private TextField searchField;
    @FXML private Button btnAdd, btnEdit, btnDelete, btnRefresh, btnSearch;

    private List<CrewMember> fullList;

    @FXML
    private void initialize() {

        colLast.setCellValueFactory(c -> c.getValue().lastNameProperty());
        colFirst.setCellValueFactory(c -> c.getValue().firstNameProperty());
        colMiddle.setCellValueFactory(c -> c.getValue().middleNameProperty());
        colRole.setCellValueFactory(c -> c.getValue().roleProperty());
        colQual.setCellValueFactory(c -> c.getValue().qualificationProperty());
        colExp.setCellValueFactory(c -> c.getValue().experienceYearsProperty());

        colFlight.setCellValueFactory(c -> {
            if (c.getValue().getFlight() != null)
                return new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFlight().getFlightNo()
                );
            return new javafx.beans.property.SimpleStringProperty("-");
        });

        loadData();

        btnRefresh.setOnAction(e -> loadData());
        btnSearch.setOnAction(e -> search());
        btnAdd.setOnAction(e -> openEditWindow(null));
        btnEdit.setOnAction(e -> openEditWindow(table.getSelectionModel().getSelectedItem()));
        btnDelete.setOnAction(e -> deleteSelected());
    }

    private void loadData() {
        try {
            fullList = CrewApi.getAll();
            table.getItems().setAll(fullList);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка загрузки данных", e.getMessage());
        }
    }

    private void search() {
        String q = searchField.getText().toLowerCase();

        List<CrewMember> filtered = fullList.stream().filter(c ->
                c.getLastName().toLowerCase().contains(q)
                        || c.getFirstName().toLowerCase().contains(q)
                        || (c.getMiddleName() != null &&
                        c.getMiddleName().toLowerCase().contains(q))
                        || c.getRole().toLowerCase().contains(q)
        ).collect(Collectors.toList());

        table.getItems().setAll(filtered);
    }

    private void deleteSelected() {
        CrewMember cm = table.getSelectionModel().getSelectedItem();
        if (cm == null) return;

        try {
            CrewApi.delete(cm.getMemberId());
            loadData();
        } catch (Exception e) {
            showError("Ошибка удаления", e.getMessage());
        }
    }

    private void openEditWindow(CrewMember member) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("/crew/CrewEditDialog.fxml"));
            Stage stage = new Stage();

            stage.setScene(new Scene(loader.load()));
            stage.setTitle(member == null ? "Добавить сотрудника" : "Редактировать сотрудника");
            stage.initModality(Modality.APPLICATION_MODAL);

            CrewEditController controller = loader.getController();
            controller.setParent(this);
            controller.setCrewMember(member);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Ошибка", e.getMessage());
        }
    }

    public void refreshTable() {
        loadData();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
