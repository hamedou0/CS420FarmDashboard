# CS420 Farm Dashboard

A JavaFX desktop application that visualizes a small "smart farm" layout and simulates a drone scanning the farm. The app is built for demonstration / coursework purposes and focuses on basic object modeling, layout visualization, and animation.

# Overview

The Farm Dashboard represents a farm as a set of containers (fields, barns, pens, etc.) and items (equipment, animals, drones, etc.). 

A drone graphic can be animated around the farm to simulate a scan: it follows a predefined path (or steps through containers/items), visually indicating that the farm is being inspected.



# Core Concepts

- **ItemContainer**
  - Represents a logical area on the farm (e.g., field, barn, storage).
  - Holds:
    - Basic metadata (`name`, `location`, `price`, `dimensions`)
    - A 2D position (`x`, `y`) on the visualization pane
    - A list of `Item` objects
    - A list of child `ItemContainer`s (supports nested structure / hierarchy)
  - Provides methods to:
    - Add/remove `Item`s
    - Add/remove `ItemContainer` children

- **Item**
  - Represents a specific object on the farm (e.g., tractor, crop patch, drone pad).
  - Includes its own metadata and dimensions.
  - Has a 2D position within the farm visualization.

- **Dimension**
  - Simple width/height/depth holder used by both `Item` and `ItemContainer`.

# UI / Visualization

- **JavaFX + FXML**
  - The main UI is defined in an FXML file (e.g. `dashboard.fxml`) and loaded by `DashboardApp`.
  - A `Pane` (e.g. `visualizationPane`) is used as a canvas to draw:
    - Rectangles or other shapes representing farm containers
    - The drone icon (e.g. a `Rectangle` or image) that moves along a path

- **Drone Visualization**
  - A visual "drone" is created in `setupDroneGraphic()`.
  - The drone is initially positioned relative to a specific `Item` (e.g., a “Drone Pad”).
  - When `animateDroneScanFarm()` is called, the drone animates around the farm:
    - Moves between multiple coordinates or container positions
    - Returns to its original starting point after completing the scan

---

## Technology Stack

- **Language:** Java 17
- **UI Toolkit:** JavaFX 21 (controls + FXML)
- **Build Tool:** Maven
- **IDE:** Designed to be used with Eclipse + Scene Builder, but any IDE that supports Maven/JavaFX will work.

---

## Project Structure (High-Level)

- `src/main/java/com/zac/farmdashboard/`
  - `DashboardApp.java`  
    - JavaFX entry point; loads the FXML layout and sets up the main stage.
  - `Item.java`  
    - Model for farm items.
  - `ItemContainer.java`  
    - Model for container areas on the farm; manages items and child containers.
  - `Dimension.java`  
    - Simple dimension holder.
  - `Controller` class(es)  
    - Handle button clicks, initialize the visualization, build containers/items, and trigger the drone animation.

- `src/main/resources/`
  - `dashboard.fxml`  
    - Main layout for the dashboard UI.
  - Images / icons (e.g. the drone PNG) if used.

- `pom.xml`
  - Maven configuration.
  - JavaFX dependencies (controls, fxml).
  - `javafx-maven-plugin` with `mainClass` configured so you can run via `mvn javafx:run`.

---

## Running the Application

### Requirements

- Java 17 (JDK)
- Maven (installed and on your PATH)

### Run

From the project root:

```bash
mvn clean javafx:run
