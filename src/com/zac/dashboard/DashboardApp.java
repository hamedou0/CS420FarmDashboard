package com.zac.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class DashboardApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Dashboard");
        stage.show();
    }
    public static void main(String[] args) { 
    	launch(args); }
}
