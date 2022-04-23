package com.company;

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

    public Order(String id, int x, int y, int timeDemand, int loadDemand) {
        super(x, y);
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
}
