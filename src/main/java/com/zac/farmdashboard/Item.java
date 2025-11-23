package com.zac.farmdashboard;

public class Item {
    private String name;
    private String location;      // human-readable label
    private double price;
    private Dimension dimensions;

    // coordinates on visualization
    private double x;
    private double y;

    public Item(String name, String location, double price, Dimension dimensions) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.dimensions = dimensions;
    }

    // basic properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Dimension getDimensions() {
        return dimensions;
    }

    public void setDimensions(Dimension dimensions) {
        this.dimensions = dimensions;
    }

    // visualization coordinates
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return name;
    }
}
