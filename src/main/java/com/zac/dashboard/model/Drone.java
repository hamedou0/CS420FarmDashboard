package com.zac.dashboard.model;

public class Drone {
    private String currentLocation;
    private DroneStatus status;
    private ItemContainer targetContainer;

    public enum DroneStatus {
        IDLE,
        VISITING,
        SCANNING
    }

    public Drone(String currentLocation) {
        this.currentLocation = currentLocation;
        this.status = DroneStatus.IDLE;
        this.targetContainer = null;
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

    public void setStatus(DroneStatus status) {
        this.status = status;
    }

    public ItemContainer getTargetContainer() {
        return targetContainer;
    }

    public void setTargetContainer(ItemContainer targetContainer) {
        this.targetContainer = targetContainer;
    }

    public void visitContainer(ItemContainer container) {
        this.targetContainer = container;
        this.status = DroneStatus.VISITING;
    }

    public void scanFarm() {
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
