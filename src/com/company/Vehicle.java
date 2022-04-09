package com.company;

import java.util.ArrayList;
import java.util.List;

public class Vehicle extends Node {
    private Depot startDepot;
    private Depot endDepot;
    private int currentLoad = 0;
    private List<Order> route = new ArrayList<>();

    public  Vehicle(Depot depot) {
        super(depot.getX(), depot.getY());
        this.startDepot = depot;
        this.endDepot = depot;
    }

    public Vehicle(Depot depot, List<Order> route) {
        super(depot.getX(), depot.getY());
        this.startDepot = depot;
        this.endDepot = depot;
        this.route = route;
        for (Order order : route) {
            this.currentLoad += order.getLoadDemand();
        }
    }

    public Vehicle(Depot startDepot, Depot endDepot, List<Order> route) {
        super(startDepot.getX(), startDepot.getY());
        this.startDepot = startDepot;
        this.endDepot = endDepot;
        this.route = route;
        for (Order order : route) {
            this.currentLoad += order.getLoadDemand();
        }
    }
}
