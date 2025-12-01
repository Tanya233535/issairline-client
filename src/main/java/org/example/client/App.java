package org.example.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showLogin();
    }

    public static void showLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/login.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);

        primaryStage.show();

        Platform.runLater(() -> primaryStage.setMaximized(true));
    }

    public static void showMainWindow(String role) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/main.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("ИССА — Главная");

        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);

        org.example.client.controller.MainController controller = loader.getController();
        controller.setRole(role);

        primaryStage.show();

        Platform.runLater(() -> {
            primaryStage.setMaximized(false);
            primaryStage.setMaximized(true);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
