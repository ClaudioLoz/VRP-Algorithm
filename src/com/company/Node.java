package com.company;

import com.company.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String mapId;
    private int x1;
    private int y1;
    private double x;
    private double y;
    private List<Node> adjacents= new ArrayList<>();

    public Node() {
    }

    public Node(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }

    public Node(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Node(String mapId, double x, double y) {
        this.mapId = mapId;
        this.x = x;
        this.y = y;
    }

    public double realDistance(Node node) {
        return Util.euclideanDistance(getX(), node.getX(), getY(), node.getY());
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public List<Node> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(List<Node> adjacents) {
        this.adjacents = adjacents;
    }
}
