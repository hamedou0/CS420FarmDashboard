package com.zac.dashboard;

import com.zac.dashboard.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class DashboardController {

    @FXML
    private TreeView<Object> treeView;

    @FXML
    private ToggleGroup droneActions;

    private ItemContainer rootContainer;
    private Drone drone;

    @FXML
    public void initialize() {
        // Initialize the root container
        rootContainer = new ItemContainer("Root", "Farm", 0.0, new Dimension(1000, 1000, 0));

        // Initialize the drone
        drone = new Drone("Command Center");

        // Set up the TreeView
        TreeItem<Object> root = new TreeItem<>(rootContainer);
        root.setExpanded(true);
        treeView.setRoot(root);

        // Add sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        // Create barn container
        ItemContainer barn = new ItemContainer("Barn", "North Field", 50000.0, new Dimension(100, 80, 30));
        rootContainer.addChildContainer(barn);

        // Create crop container
        ItemContainer crop = new ItemContainer("Crop", "South Field", 5000.0, new Dimension(200, 200, 2));
        rootContainer.addChildContainer(crop);

        // Add sample items to barn
        barn.addItem(new Item("Tractor", "Barn Bay 1", 25000.0, new Dimension(5, 3, 2)));
        barn.addItem(new Item("Hay Bales", "Barn Bay 2", 500.0, new Dimension(10, 5, 5)));

        // Update TreeView
        updateTreeView();
    }

    private void updateTreeView() {
        TreeItem<Object> root = treeView.getRoot();
        root.getChildren().clear();

        // Add child containers
        for (ItemContainer container : rootContainer.getChildContainers()) {
            TreeItem<Object> containerItem = new TreeItem<>(container);

            // Add items in the container
            for (Item item : container.getItems()) {
                containerItem.getChildren().add(new TreeItem<>(item));
            }

            // Add nested containers
            for (ItemContainer childContainer : container.getChildContainers()) {
                containerItem.getChildren().add(new TreeItem<>(childContainer));
            }

            root.getChildren().add(containerItem);
        }

        // Add items in root container
        for (Item item : rootContainer.getItems()) {
            root.getChildren().add(new TreeItem<>(item));
        }
    }

    // Event handlers for Item commands
    @FXML
    private void handleRenameItem() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Item) {
            Item item = (Item) selected.getValue();
            // TODO: Show dialog to rename item
            System.out.println("Rename item: " + item.getName());
        }
    }

    @FXML
    private void handleChangeItemLocation() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Item) {
            Item item = (Item) selected.getValue();
            // TODO: Show dialog to change location
            System.out.println("Change location for item: " + item.getName());
        }
    }

    @FXML
    private void handleChangeItemPrice() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Item) {
            Item item = (Item) selected.getValue();
            // TODO: Show dialog to change price
            System.out.println("Change price for item: " + item.getName());
        }
    }

    @FXML
    private void handleChangeItemDimensions() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Item) {
            Item item = (Item) selected.getValue();
            // TODO: Show dialog to change dimensions
            System.out.println("Change dimensions for item: " + item.getName());
        }
    }

    @FXML
    private void handleDeleteItem() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof Item) {
            Item item = (Item) selected.getValue();
            // TODO: Delete item from its container
            System.out.println("Delete item: " + item.getName());
        }
    }

    // Event handlers for ItemContainer commands
    @FXML
    private void handleRenameContainer() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to rename container
            System.out.println("Rename container: " + container.getName());
        }
    }

    @FXML
    private void handleChangeContainerLocation() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to change location
            System.out.println("Change location for container: " + container.getName());
        }
    }

    @FXML
    private void handleChangeContainerPrice() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to change price
            System.out.println("Change price for container: " + container.getName());
        }
    }

    @FXML
    private void handleChangeContainerDimensions() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to change dimensions
            System.out.println("Change dimensions for container: " + container.getName());
        }
    }

    @FXML
    private void handleAddItem() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to add new item
            System.out.println("Add item to container: " + container.getName());
        }
    }

    @FXML
    private void handleAddItemContainer() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Show dialog to add new container
            System.out.println("Add container to: " + container.getName());
        }
    }

    @FXML
    private void handleDeleteContainer() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            // TODO: Delete container
            System.out.println("Delete container: " + container.getName());
        }
    }

    // Drone action handlers
    @FXML
    private void handleVisitContainer() {
        TreeItem<Object> selected = treeView.getSelectionModel().getSelectedItem();
        if (selected != null && selected.getValue() instanceof ItemContainer) {
            ItemContainer container = (ItemContainer) selected.getValue();
            drone.visitContainer(container);
            System.out.println("Drone visiting: " + container.getName());
        }
    }

    @FXML
    private void handleScanFarm() {
        drone.scanFarm();
        System.out.println("Drone scanning farm...");
    }
}
