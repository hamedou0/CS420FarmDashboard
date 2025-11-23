package com.zac.farmdashboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemContainer {
    private String name;
    private String location;
    private double price;
    private Dimension dimensions;

    private double x;
    private double y;

    private final List<Item> items;
    private final List<ItemContainer> childContainers;

    public ItemContainer(String name, String location, double price, Dimension dimensions) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.dimensions = dimensions;
        this.items = new ArrayList<>();
        this.childContainers = new ArrayList<>();
    }

    // this is for scanning the whole farm
    public List<ItemContainer> getAllContainers() {
        List<ItemContainer> all = new ArrayList<>();
        all.add(this);

        for (ItemContainer child : childContainers) {
            all.addAll(child.getAllContainers());
        }

        return all;
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

    // coordinates
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

    // composite relationships
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public List<ItemContainer> getChildContainers() {
        return Collections.unmodifiableList(childContainers);
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addChildContainer(ItemContainer container) {
        if (container != null) {
            childContainers.add(container);
        }
    }

    public void removeChildContainer(ItemContainer container) {
        childContainers.remove(container);
    }

    @Override
    public String toString() {
        return name;
    }
}
