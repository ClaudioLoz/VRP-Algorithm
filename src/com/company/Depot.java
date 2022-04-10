package com.company;

import java.util.ArrayList;
import java.util.List;

public class Depot extends Node{
    private String id;
    private double maxDuration; // D: maximum duration of a route
    private int maxLoad; // Q: allowed maximum load of a vehicle
    private int maxVehicles; // m: maximum number of vehicles available in each depot
    private List<Order> orders;

    public Depot(int x, int y, String id, double maxDuration, int maxVehicles) {
        super(x, y);
        this.id = id;
        this.maxDuration = maxDuration;
        this.maxVehicles = maxVehicles;
    }

    public Depot(int maxDuration, int maxLoad, int maxVehicles) {
        super(0, 0);
        this.id = id;
        this.orders = new ArrayList<>();
        this.maxDuration = maxDuration;
        this.maxLoad = maxLoad;
        this.maxVehicles = maxVehicles;
    }

    public Depot(int x, int y) {
        super(x, y);
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(double maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(int maxLoad) {
        this.maxLoad = maxLoad;
    }

    public int getMaxVehicles() {
        return maxVehicles;
    }

    public void setMaxVehicles(int maxVehicles) {
        this.maxVehicles = maxVehicles;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
