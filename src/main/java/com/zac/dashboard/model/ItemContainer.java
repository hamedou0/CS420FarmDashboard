package com.zac.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class ItemContainer {
    private String name;
    private String location;
    private double price;
    private Dimension dimensions;
    private List<Item> items;
    private List<ItemContainer> childContainers;

    public ItemContainer(String name, String location, double price, Dimension dimensions) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.dimensions = dimensions;
        this.items = new ArrayList<>();
        this.childContainers = new ArrayList<>();
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

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public List<ItemContainer> getChildContainers() {
        return childContainers;
    }

    public void addChildContainer(ItemContainer container) {
        this.childContainers.add(container);
    }

    public void removeChildContainer(ItemContainer container) {
        this.childContainers.remove(container);
    }

    @Override
    public String toString() {
        return name;
    }
}
