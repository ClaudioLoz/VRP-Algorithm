package com.company;

import com.company.utils.graph.CityNode;

import java.util.ArrayList;
import java.util.List;

public class Depot{
    private String id;
    private double maxDuration; // D: maximum duration of a route
    private int maxLoad; // Q: allowed maximum load of a vehicle
    private int maxVehicles; // m: maximum number of vehicles available in each depot
    private List<Order> orders;
    private CityNode city;

    public Depot(String id,int maxLoad,int maxVehicles) {
        this.id=id;
        this.maxLoad=maxLoad;
        this.maxVehicles=maxVehicles;
        this.orders= new ArrayList<>();
    }
    public Depot( String id, int maxLoad, int maxVehicles, CityNode city){
        this.id=id;
        this.maxLoad=maxLoad;
        this.maxVehicles=maxVehicles;
        this.orders= new ArrayList<>();
        this.city = city;
    }

    public Depot(String id, double maxDuration, int maxLoad, int maxVehicles) {
        this.id = id;
        this.maxDuration = maxDuration;
        this.maxLoad= maxLoad;
        this.maxVehicles = maxVehicles;
        this.orders= new ArrayList<>();
    }

    public Depot(int maxDuration, int maxLoad, int maxVehicles) {
        this.orders = new ArrayList<>();
        this.maxDuration = maxDuration;
        this.maxLoad = maxLoad;
        this.maxVehicles = maxVehicles;
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

  public CityNode getCity() {
    return city;
  }

  public void setCity(CityNode city) {
    this.city = city;
  }
}
