package com.zac.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;

// This is the logic from the last dashboard, everything can be changed here to fit the new dashboard.
public class DashboardController {
    @FXML private BorderPane root;
    @FXML private ListView<String> items;
    @FXML private ComboBox<String> dropdown;
    @FXML private Button selectBtn;
    @FXML private Button deleteBtn;
    @FXML private TextArea messages;
    @FXML private CheckBox darkMode;

    @FXML
    private void initialize() {
        items.getItems().addAll("Item 1", "Item 2", "Item 3","Item 4", "Item ");
        dropdown.getItems().addAll("option 1", "option 2");
    }

    @FXML
    private void onSelect() {
        String select = dropdown.getValue();
        if (select != null) messages.appendText(select + " selected\n");
    }

    @FXML
    private void onDelete() {
        String delete = dropdown.getValue();
        if (delete != null) messages.appendText(delete + " deleted\n");
    }

}
