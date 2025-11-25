package org.example.client;

import javafx.application.Application;
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
        Scene scene = new Scene(loader.load(), 350, 220);
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainWindow(String role) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/main.fxml"));
        Scene scene = new Scene(loader.load(), 1000, 600);

        primaryStage.setTitle("ИССА — Главная");
        org.example.client.controller.MainController controller = loader.getController();
        controller.setRole(role);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
