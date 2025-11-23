package com.zac.farmdashboard;

public class Drone {

    public enum DroneStatus {
        IDLE,
        VISITING,
        SCANNING
    }

    private String currentLocation;
    private DroneStatus status;
    private ItemContainer targetContainer;

    public Drone(String currentLocation) {
        this.currentLocation = currentLocation;
        this.status = DroneStatus.IDLE;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public DroneStatus getStatus() {
        return status;
    }

    public ItemContainer getTargetContainer() {
        return targetContainer;
    }

    public void startVisit(ItemContainer container) {
        this.status = DroneStatus.VISITING;
        this.targetContainer = container;
    }

    public void startScan() {
        this.status = DroneStatus.SCANNING;
        this.targetContainer = null;
    }

    public void returnToIdle() {
        this.status = DroneStatus.IDLE;
        this.targetContainer = null;
    }

    @Override
    public String toString() {
        return "Drone [Status: " + status + ", Location: " + currentLocation + "]";
    }
}
