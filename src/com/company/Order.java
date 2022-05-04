package com.company;

import com.company.utils.graph.CityNode;

public class Order {
  private static int counter = 0;
  private final int id = counter ++;
  private String clientId;
    private int loadDemand;
  private CityNode city;

  
    public Order(int loadDemand, CityNode city) {
        this.loadDemand=loadDemand;
        this.city=city;
    }
    

    public int getId() {
        return id;
    }
    
    public int getLoadDemand() {
        return loadDemand;
    }

    public void setLoadDemand(int loadDemand) {
        this.loadDemand = loadDemand;
    }

  public CityNode getCity() {
    return city;
  }

  public void setCity(CityNode city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "Order{" +
        "loadDemand=" + loadDemand +
        ", city=" + city.toString() +
        '}';
  }
}
