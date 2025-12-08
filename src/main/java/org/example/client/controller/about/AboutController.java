package org.example.client.controller.about;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AboutController {

    @FXML private Label nameLabel;
    @FXML private Label universityLabel;
    @FXML private Label groupLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label projectPurposeLabel;
    @FXML private Label projectDatesLabel;

    @FXML
    private void initialize() {
        nameLabel.setText("Метелица Татьяна Максимовна");
        universityLabel.setText("Студент Финансового университета при Правительстве РФ");
        groupLabel.setText("Группа ПИ23-1");
        emailLabel.setText("233535@edu.fa.ru");
        projectPurposeLabel.setText("Система разработана для автоматизации учёта рейсов, экипажа, пассажиров, самолётов, технического обслуживания и статистической аналитики.");
        projectDatesLabel.setText("Начало: 01.09.2025, Завершение: 12.12.2025");
    }
}
