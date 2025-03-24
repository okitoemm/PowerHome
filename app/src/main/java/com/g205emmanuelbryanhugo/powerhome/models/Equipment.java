package com.g205emmanuelbryanhugo.powerhome.models;

public class Equipment {
    private int id;
    private String name;
    private double consumption;
    private boolean isActive;
    private int iconResourceId;

    public Equipment(int id, String name, double consumption, int iconResourceId) {
        this.id = id;
        this.name = name;
        this.consumption = consumption;
        this.iconResourceId = iconResourceId;
        this.isActive = false;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getConsumption() { return consumption; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
    public int getIconResourceId() { return iconResourceId; }
}
