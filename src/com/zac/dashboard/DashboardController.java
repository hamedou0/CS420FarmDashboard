package com.zac.dashboard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;

import java.io.IOException;

public class DashboardController {

    @FXML
    private StackPane mainContent;

    @FXML private ListView<String> items;
    @FXML private ComboBox<String> dropdown;
    @FXML private Button selectBtn;
    @FXML private Button deleteBtn;
    @FXML private TextArea messages;
    @FXML private CheckBox darkMode;

    @FXML
    private void initialize() {
        if (items != null)
            items.getItems().addAll("Item 1", "Item 2", "Item 3", "Item 4");

        if (dropdown != null)
            dropdown.getItems().addAll("Option 1", "Option 2");
    }

    private void switchView(String fxml) {
        try {
            // Loads from resources/views/
            Parent view = FXMLLoader.load(getClass().getResource("/views/" + fxml));
            mainContent.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void handleSoilMonitor()  { switchView("soil.fxml"); }
    @FXML private void handleCropMonitor()  { switchView("crop.fxml"); }
    @FXML private void handleWeather()      { switchView("weather.fxml"); }
    @FXML private void handleEquipment()    { switchView("equipment.fxml"); }
    @FXML private void handleLivestock()    { switchView("livestock.fxml"); }
    @FXML private void handleHarvest()      { switchView("harvest.fxml"); }
    @FXML private void handlePest()         { switchView("pest.fxml"); }
    @FXML private void handleNutrients()    { switchView("nutrients.fxml"); }
}
