package com.company;

public class Route {
    private double distance;
    private double speed;
    
    public Route(double distance) {
        this.distance = distance;
    }

  public Route(double distance, double speed) {
    this.distance = distance;
    this.speed = speed;
  }

  public Route( ) {
        
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }
}
