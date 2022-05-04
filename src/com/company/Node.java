package com.company;

import com.company.utils.Util;
import com.company.utils.graph.CityNode;

public class Node {
    private String mapId;
    private int x1;
    private int y1;
    private double latitude;
    private double longitude;
    private int matrixIndex;
    private CityNode city;
    public Node() {
    }

  public Node(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Node(CityNode city) {
        this.city = city;
    }

    public Node(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
        
    }

    public Node(double latitude, double longitude, CityNode city) {
        this.latitude = latitude;
        this.longitude = longitude;
      this.city = city;
    }

    public Node(String mapId, double latitude, double longitude, int matrixIndex) {
        this.mapId = mapId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.matrixIndex = matrixIndex;
    }
    public Node(String mapId, double latitude, double longitude, CityNode city) {
        this.mapId = mapId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.matrixIndex = matrixIndex;
        this.city= city;
    }

    public Node(String mapId, double latitude, double longitude) {
        this.mapId = mapId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    

    public double distance(Node node) {
        return Util.euclideanDistance(getX1(), node.getX1(), getY1(), node.getY1());
    }

    public void setCoordinates(int x,int y){
        this.x1 =x;
        this.y1 =y;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getMatrixIndex() {
        return matrixIndex;
    }

    public void setMatrixIndex(int matrixIndex) {
        this.matrixIndex = matrixIndex;
    }

  public CityNode getCity() {
    return city;
  }

  public void setCity(CityNode city) {
    this.city = city;
  }

  @Override
    public String toString() {
        return mapId+"(x: "+ latitude +" y:"+ longitude +")";
    }
}
