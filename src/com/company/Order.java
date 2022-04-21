package com.company;

public class Order extends Node{
    private String id; // i: order number
    private int timeDemand;
    private int loadDemand;
    private String idOffice;

    public Order(String mapId, double x, double y) {
        super(mapId, x, y);
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

    public String getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(String idOffice) {
        this.idOffice = idOffice;
    }
}
