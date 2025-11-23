package com.zac.farmdashboard;

import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

public class DashboardController {

    @FXML
    private TreeView<Object> treeView;

    @FXML
    private Pane visualizationPane;

    // Composite root
    private ItemContainer rootContainer;

    // Drone item reference
    private Item droneItem;
    private Node droneView;

    // Mapping for visualization
    private final Map<Object, Node> visualNodes = new HashMap<>();


    
    // INITIALIZE
    
    @FXML
    public void initialize() {

        rootContainer = new ItemContainer(
                "Root Farm",
                "Farm",
                0.0,
                new Dimension(800, 600, 0)
        );

        initializeSampleScenario();

        TreeItem<Object> root = new TreeItem<>(rootContainer);
        root.setExpanded(true);
        treeView.setRoot(root);

        updateTreeView();
        drawVisualization();
        setupDroneGraphic();
    }


    
    private void initializeSampleScenario() {

        ItemContainer barn = new ItemContainer("Barn", "North Field", 50000, new Dimension(200,150,30));
        barn.setPosition(100,350);
        rootContainer.addChildContainer(barn);

        ItemContainer storage = new ItemContainer("Storage Building", "East Yard", 20000, new Dimension(200,150,30));
        storage.setPosition(450,350);
        rootContainer.addChildContainer(storage);

        Item tractor = new Item("Tractor", "Bay 1", 25000, new Dimension(40,40,20));
        tractor.setPosition(470,400);
        storage.addItem(tractor);

        Item tiller = new Item("Tiller", "Bay 2", 8000, new Dimension(40,40,20));
        tiller.setPosition(530,400);
        storage.addItem(tiller);


        ItemContainer commandCenter = new ItemContainer("Command Center", "Central Hub", 0, new Dimension(150,100,20));
        commandCenter.setPosition(300,100);
        rootContainer.addChildContainer(commandCenter);

        droneItem = new Item("Drone", "Pad", 0, new Dimension(50,50,10));
        droneItem.setPosition(330,115);

    }



   
    // TREE VIEW
    
    private void updateTreeView() {
        TreeItem<Object> rootNode = treeView.getRoot();
        if (rootNode == null) {
            rootNode = new TreeItem<>(rootContainer);
            rootNode.setExpanded(true);
            treeView.setRoot(rootNode);
        }
        rootNode.getChildren().clear();
        populateTree(rootNode, rootContainer);
    }

    private void populateTree(TreeItem<Object> parent, ItemContainer container) {

        for (ItemContainer c : container.getChildContainers()) {
            TreeItem<Object> node = new TreeItem<>(c);
            node.setExpanded(true);
            parent.getChildren().add(node);
            populateTree(node, c);
        }

        for (Item item : container.getItems()) {
            parent.getChildren().add(new TreeItem<>(item));
        }
    }


    
    // INPUT HELPERS
    
    private String promptString(String title, String header, String old) {
        TextInputDialog d = new TextInputDialog(old);
        d.setTitle(title); d.setHeaderText(header);
        return d.showAndWait().orElse(null);
    }

    private Double promptDouble(String title, String header, double old) {
        TextInputDialog d = new TextInputDialog(String.valueOf(old));
        d.setTitle(title); d.setHeaderText(header);
        Optional<String> r = d.showAndWait();
        if (r.isPresent()) {
            try { return Double.parseDouble(r.get()); }
            catch (NumberFormatException e) { showError("Invalid number."); }
        }
        return null;
    }

    private void updateTreeAndView() {
        updateTreeView();
        drawVisualization();
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.setHeaderText("Error"); a.show();
    }

    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText("Drone"); a.show();
    }

    private Object getSelectedValue() {
        TreeItem<Object> node = treeView.getSelectionModel().getSelectedItem();
        if (node == null) {
            showError("Select something from the tree.");
            return null;
        }
        return node.getValue();
    }



   
    // ITEM COMMANDS
    
    @FXML
    private void handleRenameItem() {
        Object v = getSelectedValue();
        if (!(v instanceof Item item)) { showError("Select an Item."); return; }

        String name = promptString("Rename Item", "New name:", item.getName());
        if (name != null && !name.isBlank()) {
            item.setName(name.trim());
            updateTreeAndView();
        }
    }

    @FXML
    private void handleChangeItemLocation() {
        Object v = getSelectedValue();
        if (!(v instanceof Item item)) { showError("Select an Item."); return; }

        String loc = promptString("Change Location", "New location label:", item.getLocation());
        if (loc != null && !loc.isBlank()) {
            item.setLocation(loc.trim());
            updateTreeAndView();
        }
    }

    @FXML
    private void handleChangeItemPrice() {
        Object v = getSelectedValue();
        if (!(v instanceof Item item)) { showError("Select an Item."); return; }

        Double p = promptDouble("Change Price", "New price:", item.getPrice());
        if (p != null) { item.setPrice(p); updateTreeAndView(); }
    }

    @FXML
    private void handleChangeItemDimensions() {
        Object v = getSelectedValue();
        if (!(v instanceof Item item)) { showError("Select an Item."); return; }

        Dimension d = item.getDimensions();

        Double w = promptDouble("Width", "New width:", d.getWidth());
        if (w == null) return;
        Double h = promptDouble("Height", "New height:", d.getHeight());
        if (h == null) return;
        Double dep = promptDouble("Depth", "New depth:", d.getDepth());
        if (dep == null) return;

        item.setDimensions(new Dimension(w, h, dep));
        updateTreeAndView();
    }

    @FXML
    private void handleDeleteItem() {
        Object v = getSelectedValue();
        if (!(v instanceof Item item)) { showError("Select an Item."); return; }

        TreeItem<Object> node = treeView.getSelectionModel().getSelectedItem();
        TreeItem<Object> parentNode = node.getParent();
        if (!(parentNode.getValue() instanceof ItemContainer parent)) {
            showError("Item missing parent."); return;
        }

        parent.removeItem(item);
        updateTreeAndView();
    }



    
    // CONTAINER COMMANDS
    
    @FXML
    private void handleRenameContainer() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer c)) { showError("Select a container."); return; }

        String name = promptString("Rename Container", "New name:", c.getName());
        if (name != null && !name.isBlank()) {
            c.setName(name.trim());
            updateTreeAndView();
        }
    }

    @FXML
    private void handleChangeContainerLocation() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer c)) { showError("Select a container."); return; }

        String loc = promptString("Change Location", "New location label:", c.getLocation());
        if (loc != null && !loc.isBlank()) {
            c.setLocation(loc.trim());
            updateTreeAndView();
        }
    }

    @FXML
    private void handleChangeContainerPrice() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer c)) { showError("Select a container."); return; }

        Double p = promptDouble("Change Price", "New price:", c.getPrice());
        if (p != null) { c.setPrice(p); updateTreeAndView(); }
    }

    @FXML
    private void handleChangeContainerDimensions() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer c)) { showError("Select a container."); return; }

        Dimension d = c.getDimensions();

        Double w = promptDouble("Width", "New width:", d.getWidth());
        if (w == null) return;
        Double h = promptDouble("Height", "New height:", d.getHeight());
        if (h == null) return;
        Double dep = promptDouble("Depth", "New depth:", d.getDepth());
        if (dep == null) return;

        c.setDimensions(new Dimension(w, h, dep));
        updateTreeAndView();
    }

    @FXML
    private void handleAddItem() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer parent)) { showError("Select container."); return; }

        String name = promptString("Add Item", "Name:", "New Item");
        if (name == null) return;

        String loc = promptString("Location Label", "Location:", "Unknown");
        if (loc == null) return;

        Double price = promptDouble("Price", "Item price:", 0);
        if (price == null) return;

        Item item = new Item(name, loc, price, new Dimension(40,40,20));
        item.setPosition(parent.getX()+20, parent.getY()+20);

        parent.addItem(item);
        updateTreeAndView();
    }

    @FXML
    private void handleAddItemContainer() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer parent)) { showError("Select container."); return; }

        String name = promptString("Add Container", "Name:", "New Container");
        if (name == null) return;

        String loc = promptString("Location Label", "Location:", "Unknown");
        if (loc == null) return;

        Double price = promptDouble("Price", "Container price:", 0);
        if (price == null) return;

        ItemContainer c = new ItemContainer(name, loc, price, new Dimension(120,90,30));
        c.setPosition(parent.getX()+40, parent.getY()+40);

        parent.addChildContainer(c);
        updateTreeAndView();
    }

    @FXML
    private void handleDeleteContainer() {
        Object v = getSelectedValue();
        if (!(v instanceof ItemContainer c)) { showError("Select container."); return; }
        if (c == rootContainer) { showError("Cannot delete root."); return; }

        TreeItem<Object> node = treeView.getSelectionModel().getSelectedItem();
        TreeItem<Object> parentNode = node.getParent();

        if (!(parentNode.getValue() instanceof ItemContainer parent)) {
            showError("Invalid parent container.");
            return;
        }

        parent.removeChildContainer(c);
        updateTreeAndView();
    }



    
    // DRONE ACTIONS (Visit + Scan)
    
    @FXML
    private void handleVisitContainer() {
        Object target = getSelectedValue();
        if (target != null) animateDroneTo(target);
    }

    @FXML
    private void handleScanFarm() {
        animateDroneScanFarm();
    }


    
    // ATTRIBUTE ACTIONS
    
    @FXML private void handleSoilMoisture()       { performDroneAction("Soil Moisture"); }
    @FXML private void handleCropGrowth()        { performDroneAction("Crop Growth"); }
    @FXML private void handleWeather()           { performDroneAction("Weather"); }
    @FXML private void handleLivestock()         { performDroneAction("Livestock Monitoring"); }
    @FXML private void handleHarvest()           { performDroneAction("Harvest Readiness"); }
    @FXML private void handlePestDetection()     { performDroneAction("Pest Detection"); }
    @FXML private void handleNutrientLevels()    { performDroneAction("Nutrient Levels"); }
    @FXML private void handleEquipmentTracking() { performDroneAction("Equipment Tracking"); }


    private void performDroneAction(String action) {

        Object target = getSelectedValue();
        if (target == null) return;

        if (!validateDroneTask(action, target)) {
            showError(buildInvalidMessage(action));
            return;
        }

        animateDroneTo(target);
        showInfo("Drone started \"" + action + "\" on " + target);
    }


    
    // DRONE VALIDATION SYSTEM
    
    private boolean validateDroneTask(String action, Object target) {

        boolean isItem = target instanceof Item;
        boolean isContainer = target instanceof ItemContainer;

        String name = target.toString().toLowerCase();

        boolean isCrop = name.matches(".*(soy|corn|crop|field|wheat|plant|orchard).*");
        boolean isLivestock = name.matches(".*(cow|sheep|pig|goat|chicken|livestock).*");
        boolean isEquipment = name.matches(".*(tractor|tiller|plow|hoe|harvester|sprayer|equipment).*");


        // Weather allowed on containers + crops
        if (action.equals("Weather"))
            return isContainer || isCrop;

        // Crop actions
        if (List.of("Soil Moisture","Crop Growth","Harvest Readiness","Nutrient Levels","Pest Detection").contains(action))
            return isCrop;

        // Livestock
        if (action.equals("Livestock Monitoring"))
            return isLivestock;

        // Equipment
        if (action.equals("Equipment Tracking"))
            return isEquipment;

        return true;
    }


    private String buildInvalidMessage(String action) {
        switch (action) {
            case "Weather": return "Weather only works on containers or crop areas.";
            case "Livestock Monitoring": return "Livestock monitoring requires animals (Cow, Sheep, etc).";
            case "Equipment Tracking": return "Equipment tracking requires equipment (Tractor, Harvester, etc).";
            default: return action + " requires a crop item.";
        }
    }



   
    // VISUALIZATION (WITH COLOR CODING)
    
    private void drawVisualization() {

        visualizationPane.getChildren().clear();
        visualNodes.clear();

        drawComponent(rootContainer);

        if (droneView != null)
            visualizationPane.getChildren().add(droneView);
    }


    private void drawComponent(Object obj) {

        if (obj instanceof ItemContainer container) {

            Rectangle r = new Rectangle(
                    container.getX(),
                    container.getY(),
                    container.getDimensions().getWidth(),
                    container.getDimensions().getHeight()
            );
            r.setStroke(Color.BLACK);
            r.setFill(Color.web("#D9D9D9"));  // containers = light gray

            Text label = new Text(container.getX() + 4,
                    container.getY() + 16,
                    container.getName());

            Group g = new Group(r, label);
            visualizationPane.getChildren().add(g);
            visualNodes.put(container, g);

            for (ItemContainer child : container.getChildContainers())
                drawComponent(child);

            for (Item item : container.getItems())
                drawComponent(item);
        }

        else if (obj instanceof Item item) {

            String name = item.getName().toLowerCase();

            Color fillColor = Color.LIGHTGRAY;

            if (name.matches(".*(soy|corn|wheat|crop|field|plant).*"))
                fillColor = Color.web("#77CC77");  // crops = green

            else if (name.matches(".*(cow|sheep|goat|pig|chicken).*"))
                fillColor = Color.web("#F4A460");  // livestock = orange

            else if (name.matches(".*(tractor|tiller|plow|harvester|hoe|equipment).*"))
                fillColor = Color.web("#FFD700");  // equipment = gold-yellow


            Rectangle r = new Rectangle(
                    item.getX(),
                    item.getY(),
                    item.getDimensions().getWidth(),
                    item.getDimensions().getHeight()
            );
            r.setStroke(Color.DARKGRAY);
            r.setFill(fillColor);

            Text label = new Text(item.getX() + 4,
                    item.getY() + 14,
                    item.getName());

            Group g = new Group(r, label);
            visualizationPane.getChildren().add(g);
            visualNodes.put(item, g);
        }
    }



    
    // DRONE GRAPHIC + ANIMATION
    
    private void setupDroneGraphic() {

        Image droneImage = new Image(
                getClass().getResource("/drone.png").toExternalForm(),
                60, 60, true, true
        );

        ImageView drone = new ImageView(droneImage);

        this.droneView = drone;

        snapDroneToItem(droneItem);
        visualizationPane.getChildren().add(droneView);
        
        
    }
    


    private void snapDroneToItem(Item item) {
        droneView.setLayoutX(item.getX() + item.getDimensions().getWidth()/2 - 15);
        droneView.setLayoutY(item.getY() + item.getDimensions().getHeight()/2 - 15);
    }


    private void animateDroneTo(Object target) {

        Node node = visualNodes.get(target);
        if (node == null) return;

        Bounds b = node.getBoundsInParent();
        double endX = b.getMinX() + b.getWidth()/2 - 15;
        double endY = b.getMinY() + b.getHeight()/2 - 15;

        double startX = droneView.getLayoutX();
        double startY = droneView.getLayoutY();

        TranslateTransition tt =
                new TranslateTransition(javafx.util.Duration.seconds(2), droneView);

        tt.setFromX(0); tt.setFromY(0);
        tt.setToX(endX - startX);
        tt.setToY(endY - startY);

        tt.setOnFinished(e -> {
            droneView.setLayoutX(endX);
            droneView.setLayoutY(endY);
            droneView.setTranslateX(0);
            droneView.setTranslateY(0);
        });

        tt.play();
    }
    


    private void animateDroneScanFarm() {
        if (droneView == null) return;

        // capture the original location
        double startX = droneView.getLayoutX();
        double startY = droneView.getLayoutY();
        Point2D startPoint = new Point2D(startX, startY);

        double margin = 40;

        double left   = margin;
        double top    = margin;
        double right  = visualizationPane.getWidth()  - margin;
        double bottom = visualizationPane.getHeight() - margin;

        List<Point2D> points = List.of(
            new Point2D(left, top),
            new Point2D(right, top),
            new Point2D(right, bottom),
            new Point2D(left, bottom),
            new Point2D(left, top),       
            startPoint                    
        );

        SequentialTransition seq = new SequentialTransition();

        for (Point2D p : points) {
            seq.getChildren().add(createMoveTo(p, javafx.util.Duration.seconds(1.5)));
        }

        seq.play();
    }



    private TranslateTransition createMoveTo(Point2D target, javafx.util.Duration duration) {
        TranslateTransition tt = new TranslateTransition(duration, droneView);
        tt.setToX(target.getX() - droneView.getLayoutX());
        tt.setToY(target.getY() - droneView.getLayoutY());
        return tt;
    }


}
