package com.company;

public class Route {
    private double distance;
    private double speed;
    private Boolean connected;
    
    public Route(double distance) {
        this.distance = distance;
    }

  public Route(double distance, double speed) {
    this.distance = distance;
    this.speed = speed;
  }
  public Route(double distance, double speed, Boolean connected) {
    this.distance = distance;
    this.speed = speed;
    this.connected = connected;
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

  public void setConnected(Boolean connected){
        this.connected = connected;
  }

  public Boolean getConnected(){
        return this.connected;
  }
  @Override
  public String toString() {
    return speed != 0 ? Double.toString(distance/speed) : "0";
  }
}
