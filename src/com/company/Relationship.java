package com.company;

import static com.company.utils.Util.euclideanDistance;

public class Relationship {
    private double distance;
    public Relationship( double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
