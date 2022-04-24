package com.company;

import com.company.utils.Util;
import com.company.utils.graph.CityNode;
import com.company.utils.graph.FindPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.company.utils.MockGraph.GRAPH;

public class Vehicle extends Node {
    private String id;
    private Depot startDepot;
    private Depot endDepot;
    private int currentLoad = 0;
    private List<Order> route = new ArrayList<>();

    public  Vehicle(Depot depot) {
        super(depot.getX1(), depot.getY1());
        this.startDepot = depot;
        this.endDepot = depot;
    }

    public Vehicle(Depot depot, List<Order> route) {
        super(depot.getX1(), depot.getY1());
        this.startDepot = depot;
        this.endDepot = depot;
        this.route = route;
        for (Order order : route) {
            this.currentLoad += order.getLoadDemand();
        }
    }

    public Vehicle(Depot startDepot, Depot endDepot, List<Order> route) {
        super(startDepot.getX1(), startDepot.getY1());
        this.startDepot = startDepot;
        this.endDepot = endDepot;
        this.route = route;
        for (Order order : route) {
            this.currentLoad += order.getLoadDemand();
        }
    }



    public int getLoadIfOrderAdded(Order order) {
        return currentLoad + order.getLoadDemand();
    }

    public int getLoadIfRouteAdded(List<Order> route) {
        return currentLoad + getRouteLoad(route);
    }

    private int getRouteLoad(List<Order> route) {
        int routeLoad = 0;

        for (Order order : route) {
            routeLoad += order.getLoadDemand();
        }

        return routeLoad;
    }

    public double calculateRouteDuration() {
        double routeDistance = 0.0;

        if (route.size() == 0) {
            return routeDistance;
        }

      routeDistance += FindPath.calculateShortestPath(GRAPH,startDepot.getCity(),route.get(0).getCity() ); //start depot to first order
//        duration += copy.get(0).getTimeDemand();//time in that order ( 1 hour) so it have to be changed maybe with a speed factor
      for (int i = 0; i < route.size() - 1; i++) {
//            duration += copy.get(i).distance(copy.get(i + 1));
        routeDistance += FindPath.calculateShortestPath(GRAPH,route.get(i).getCity(), route.get(i+1).getCity() );
//            duration += copy.get(i + 1).getTimeDemand();
      }
//        duration += copy.get(copy.size() - 1).distance(endDepot); //end order to end depot
      //routeDistance += FindPath.calculateShortestPath(GRAPH,route.get(route.size()-1).getCity(),endDepot.getCity());
        return routeDistance;
    }

    public double calculateRouteDurationIfOrderAdded(int index, Order orderToCheck) {
        if (route.size() == 0) {
          return FindPath.calculateShortestPath(GRAPH,startDepot.getCity(), endDepot.getCity() ) ;
        }

        double duration = 0.0;
        List<Order> copy = new ArrayList<>(route);
        copy.add(index, orderToCheck);

        duration += FindPath.calculateShortestPath(GRAPH,startDepot.getCity(),copy.get(0).getCity() ); //start depot to first order
//        duration += copy.get(0).getTimeDemand();//time in that order ( 1 hour) so it have to be changed maybe with a speed factor
        for (int i = 0; i < copy.size() - 1; i++) {
//            duration += copy.get(i).distance(copy.get(i + 1));
            duration += FindPath.calculateShortestPath(GRAPH,copy.get(i).getCity(), copy.get(i+1).getCity() );
//            duration += copy.get(i + 1).getTimeDemand();
        }
//        duration += copy.get(copy.size() - 1).distance(endDepot); //end order to end depot
      //duration += FindPath.calculateShortestPath(GRAPH,copy.get(copy.size()-1).getCity(),endDepot.getCity());
      return duration;
    }


    public List<Order>[] crossMutate(List<Order> otherRoute) {
        final List<Order>[] subRoutes = Util.splitRoute(route);
        final List<Order>[] otherSubRoutes = Util.splitRoute(otherRoute);
        List<Order> mutatedRoute = merge(subRoutes[0], otherSubRoutes[1]);
        List<Order> mutatedRoute2 = merge(otherSubRoutes[0], subRoutes[1]);

        return new List[]{mutatedRoute, mutatedRoute2};
    }


    private List<Order> merge(List<Order> subRoute, List<Order> otherSubRoute) {
        List<Order> mergedRoute = new ArrayList<>(subRoute);
        mergedRoute.addAll(otherSubRoute);
        return mergedRoute;
    }


    public List<Order> swapMutate() {
        List<Order> newRoute = new ArrayList<>(route);

        if (newRoute.size() <= 1) {
            return newRoute;
        }

        int indexA = 0;
        int indexB = 0;

        while (indexA == indexB) {
            indexA = Util.randomIndex(newRoute.size());
            indexB = Util.randomIndex(newRoute.size());
        }

        Collections.swap(newRoute, indexA, indexB);

        return newRoute;
    }


    public boolean addOrderToRoute(Order order) {
        route.add(order);
        currentLoad += order.getLoadDemand();
        return true;
    }

    public void addOrderToRoute(int index, Order order) {
        route.add(index, order);
        currentLoad += order.getLoadDemand();
    }

    public void removeOrderFromRoute(Order order) {
        route.remove(order);
        currentLoad -= order.getLoadDemand();
    }

    @Override
    public Vehicle clone() {
        List<Order> copyOfRoute = new ArrayList<>(route);
        return new Vehicle(startDepot, endDepot, copyOfRoute);
    }

    public void addOtherRouteToRoute(int index, List<Order> otherRoute) {
        for (Order c : otherRoute) {
            currentLoad += c.getLoadDemand();
        }

        route.addAll(index, otherRoute);
    }
    
    

    public boolean smartAddOrderToRoute(Order orderToAdd, boolean force) {
        double minDuration = Double.MAX_VALUE;
        int minIndex = -1;
        //we already assigned orders to their nearest depots, so now we only care  about vehicle capacity and route(order of orders lol)
      double currentTime=getCurrentTime();
      CityNode city = route.size() == 0 ? startDepot.getCity() : route.get(route.size()-1).getCity();
        if ((currentLoad + orderToAdd.getLoadDemand() > startDepot.getMaxLoad())   && !force) {
//            int allowableLoad = startDepot.getMaxLoad()-currentLoad;// for partial deliveries
//            if(allowableLoad>0){
//                System.out.println("Pedido original: "+ orderToAdd.getId()+" "+orderToAdd.getLoadDemand());
//                System.out.println(this+" "+currentLoad+" "+allowableLoad);
//                orderToAdd.setLoadDemand(orderToAdd.getLoadDemand()-allowableLoad);
//                System.out.println(orderToAdd.getLoadDemand());
//                addOrderToRoute(orderToAdd);
//            }
          return false;
        }
        else if( (currentTime +  FindPath.calculateShortestPath(GRAPH, city, orderToAdd.getCity())) > 25 && !force){
          return false;
        }
        else if (route.size() == 0) {
            addOrderToRoute(orderToAdd);
            return true;
        } else {
            for (int i = 0; i < route.size(); i++) {
                double duration = calculateRouteDurationIfOrderAdded(i, orderToAdd);
                if (duration < minDuration) {
                    minDuration = duration;
                    minIndex = i;
                }
            }

            addOrderToRoute(minIndex, orderToAdd);
            return true;
        }
    }

  private double getCurrentTime() {
    double currentTime=0;
    if(route.size()==0)
      return 0;
    for (int i=0;i <route.size();i++) {
      Order order= route.get(i);
      if(i==0) currentTime+= FindPath.calculateShortestPath(GRAPH, startDepot.getCity(), order.getCity());
      //else if(i==route.size()-1)return currentTime+ FindPath.calculateShortestPath(GRAPH, order.getCity(), endDepot.getCity());
      else if(i==route.size()-1)return currentTime;
      else currentTime+= FindPath.calculateShortestPath(GRAPH, order.getCity(),route.get(i+1).getCity());
    }
    return currentTime;
  }

  public void removeRouteFromRoute(List<Order> otherRoute) {
        for (Order c : otherRoute) {
            currentLoad -= c.getLoadDemand();
        }

        route.removeAll(otherRoute);
    }

    public Depot getStartDepot() {
        return startDepot;
    }

    public void setStartDepot(Depot startDepot) {
        this.startDepot = startDepot;
    }

    public Depot getEndDepot() {
        return endDepot;
    }

    public void setEndDepot(Depot endDepot) {
        this.endDepot = endDepot;
    }

    public int getCurrentLoad() {
        return currentLoad;
    }

    public void setCurrentLoad(int currentLoad) {
        this.currentLoad = currentLoad;
    }

    public List<Order> getRoute() {
        return route;
    }

    public void setRoute(List<Order> route) {
        this.route = route;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
