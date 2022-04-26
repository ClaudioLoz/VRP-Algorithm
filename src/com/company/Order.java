package com.company;

import com.company.utils.graph.CityNode;

public class Order extends Node{
    private String id; // i: order number
    private int timeDemand;
    private int loadDemand;

    public Order(Node node, String id, int timeDemand, int loadDemand) {
        super(node.getMapId(), node.getX(), node.getY(),node.getMatrixIndex());
        this.id=id;
        this.timeDemand=timeDemand;
        this.loadDemand=loadDemand;
    }
    public Order(Node node, String id, int timeDemand, int loadDemand, CityNode city) {
        super(node.getMapId(), node.getX(), node.getY(),node.getMatrixIndex());
        this.id=id;
        this.timeDemand=timeDemand;
        this.loadDemand=loadDemand;
        this.setCity(city);
    }

    public Order(String id, int x, int y, int timeDemand, int loadDemand) {
        super(x, y);
        this.id = id;
        this.timeDemand = timeDemand;
        this.loadDemand = loadDemand;
    }
    public Order(String id, int x, int y, int timeDemand, int loadDemand, CityNode city) {
        super(x, y, city);
        this.id = id;
        this.timeDemand = timeDemand;
        this.loadDemand = loadDemand;
    }

    public Order(String id,int timeDemand, int loadDemand){
        super();
        this.id = id;
        this.timeDemand = timeDemand;
        this.loadDemand = loadDemand;
    }
    public Order(String id, int timeDemand, int loadDemand, CityNode city){
        super(city);
        this.id = id;
        this.timeDemand = timeDemand;
        this.loadDemand = loadDemand;
    }

    @Override
    protected Order clone()  {
        Order order= new Order(new Node(this.getMapId(),this.getX(),this.getY(),this.getCity()),this.getId(),this.timeDemand,this.loadDemand,this.getCity());
        return order;
    }

    public Order(int x, int y) {
        super(x, y);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTimeDemand() {
        return timeDemand;
    }

    public void setTimeDemand(int timeDemand) {
        this.timeDemand = timeDemand;
    }

    public int getLoadDemand() {
        return loadDemand;
    }

    public void setLoadDemand(int loadDemand) {
        this.loadDemand = loadDemand;
    }


    @Override
    public String toString() {
        return id+" cantidad de paquetes: "+ loadDemand;
    }
}
