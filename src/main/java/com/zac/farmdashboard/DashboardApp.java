package com.zac.farmdashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardApp extends Application {

    private static DashboardApp instance;
    private Stage primaryStage;

    public DashboardApp() {
        instance = this;
    }

    public static DashboardApp getInstance() {
        return instance;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Farm Dashboard");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
