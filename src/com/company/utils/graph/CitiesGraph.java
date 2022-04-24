package com.company.utils.graph;

//https://localcoder.org/calculating-distance-between-non-directly-connected-nodes-in-matrix
import com.company.Route;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class CitiesGraph{

  //use set which prevents duplicate entries
  private final Set<CityNode> cities;
  private final Route[][] adjacencyMatrix;
  private static final int NOT_CONNECTED = -1;

  public  CitiesGraph(Set<CityNode> cities) {
    this.cities = cities;
    adjacencyMatrix = new Route[cities.size()][cities.size()];
    //initialize matrix
    for(int row = 0; row < adjacencyMatrix.length ; row++){
      for(int col = 0; col < adjacencyMatrix[row].length ; col++){
        adjacencyMatrix[row][col] = row == col ? new Route(0,0) : new Route(NOT_CONNECTED,0);
      }
    }
  }

  public void connectCities(CityNode city1, CityNode city2, Route distance) {
    //assuming undirected graph
    adjacencyMatrix[city1.getId()][city2.getId()] = distance;
    adjacencyMatrix[city2.getId()][city1.getId()] = distance;
  }

  public double getDistanceBetween(CityNode city1, CityNode city2) {

    return adjacencyMatrix[city1.getId()][city2.getId()].getDistance();
  }

  public double getTimeBetween(CityNode city1, CityNode city2) {
    Route route = adjacencyMatrix[city1.getId()][city2.getId()];
    return route.getSpeed() != 0 ? route.getDistance()/route.getSpeed() : 0;
  }
  
  public Collection<CityNode> getCitiesConnectedTo(CityNode city) {

    Collection<CityNode> connectedCities = new ArrayList<>();
    //iterate over row representing city's connections
    int column = 0;
    for(Route distance : adjacencyMatrix[city.getId()]){
      if(distance.getDistance() != NOT_CONNECTED && distance.getDistance() > 0) {
        connectedCities.add(getCityById(column));
      }
      column++;
    }

    return connectedCities;
  }

  public CityNode getCityById(int id) {
    for (CityNode city : cities) {
      if (city.getId() == id) return city;
    }
    return null;
  }

  public void show() {
    for(Route[] row : adjacencyMatrix){
      System.out.println(Arrays.toString(row));
    }
  }

  //get a copy of cities list
  public Collection<CityNode> getCities(){
    return new ArrayList<>(cities);
  }
}