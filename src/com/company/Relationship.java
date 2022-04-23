package com.company;

import static com.company.utils.Util.euclideanDistance;

public class Relationship {
    private double distance;
    private double speed;
    
    public Relationship( double distance) {
        this.distance = distance;
    }

  public Relationship(double distance, double speed) {
    this.distance = distance;
    this.speed = speed;
  }

  public Relationship( ) {
        
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
