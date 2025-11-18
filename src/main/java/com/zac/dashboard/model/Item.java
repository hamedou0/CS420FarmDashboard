package com.zac.dashboard.model;

public class Item {
    private String name;
    private String location;
    private double price;
    private Dimension dimensions;

    public Item(String name, String location, double price, Dimension dimensions) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.dimensions = dimensions;
    }

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

    @Override
    public String toString() {
        return name;
    }
}
