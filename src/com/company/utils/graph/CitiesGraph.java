package com.company.utils.graph;

//https://localcoder.org/calculating-distance-between-non-directly-connected-nodes-in-matrix
import com.company.Route;

import java.util.*;

import static com.company.utils.Constants.*;
import static com.company.utils.Util.haversine;

public class CitiesGraph{

  //use set which prevents duplicate entries
  private final Set<CityNode> cities;
  private final Route[][] adjacencyMatrix;



  public  CitiesGraph(Set<CityNode> cities) {
    this.cities = cities;
    adjacencyMatrix = new Route[cities.size()][cities.size()];
    //initialize matrix
    for(int row = 0; row < adjacencyMatrix.length ; row++){
      for(int col = 0; col < adjacencyMatrix[row].length ; col++){
        adjacencyMatrix[row][col] = row == col ? new Route(0, 0, CONNECTED) : new Route(0, 0, NOT_CONNECTED);
      }
    }
  }

  public void connectCities(CityNode city1, CityNode city2, Route distance) {
    //assuming undirected graph
    adjacencyMatrix[city1.getId()][city2.getId()] = distance;
    adjacencyMatrix[city2.getId()][city1.getId()] = distance;
  }

  public void connectCities(CityNode city1, CityNode city2) {
    //assuming undirected graph
    Route route = null;
    double distance = haversine(city1, city2);
    if (Objects.equals(city1.getRegion(), COSTA) && Objects.equals(city2.getRegion(), COSTA)) {
      route = new Route(distance, V_COSTA_COSTA);
    }
    if (Objects.equals(city1.getRegion(), SIERRA) && Objects.equals(city2.getRegion(), SIERRA)) {
      route = new Route(distance, V_SIERRA_SIERRA);
    }
    if (Objects.equals(city1.getRegion(), SELVA) && Objects.equals(city2.getRegion(), SELVA)) {
      route = new Route(distance, V_SELVA_SELVA);
    }
    if (Objects.equals(city1.getRegion(), COSTA) && Objects.equals(city2.getRegion(), SIERRA) || Objects.equals(city1.getRegion(), SIERRA) && Objects.equals(city2.getRegion(), COSTA)) {
      route = new Route(distance, V_COSTA_SIERRA);
    }
    if (Objects.equals(city1.getRegion(), SELVA) && Objects.equals(city2.getRegion(), SIERRA) || Objects.equals(city1.getRegion(), SIERRA) && Objects.equals(city2.getRegion(), SELVA)) {
      route = new Route(distance, V_SIERRA_SELVA);
    }
    if (Objects.equals(city1.getRegion(), SELVA) && Objects.equals(city2.getRegion(), COSTA) || Objects.equals(city1.getRegion(), COSTA) && Objects.equals(city2.getRegion(), SELVA)) {
      route = new Route(distance, V_COSTA_SELVA);
    }
    if (route != null) {
      adjacencyMatrix[city1.getId()][city2.getId()] = route;
      adjacencyMatrix[city2.getId()][city1.getId()] = route;
    }
  }

  public double getDistanceBetween(CityNode city1, CityNode city2) {

    return adjacencyMatrix[city1.getId()][city2.getId()].getDistance();
  }

  public double getTimeBetween(CityNode city1, CityNode city2) {
    Route route = adjacencyMatrix[city1.getId()][city2.getId()];
    return route.getSpeed() > 0 ? route.getDistance() / route.getSpeed() : 0;
  }
  
  public Collection<CityNode> getCitiesConnectedTo(CityNode city) {

    Collection<CityNode> connectedCities = new ArrayList<>();
    //iterate over row representing city's connections
    int column = 0;
    for(Route distance : adjacencyMatrix[city.getId()]){
      if (distance.getConnected() != NOT_CONNECTED && distance.getDistance() > 0) {
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

  public boolean areAdjacent(CityNode from, CityNode currentCity) {
    return adjacencyMatrix[from.getId()][currentCity.getId()].getDistance() > 0;
  }

  public void blockPath(CityNode city1, CityNode city2) {
    if (areAdjacent(city1, city2)) {
      adjacencyMatrix[city1.getId()][city2.getId()] = new Route(adjacencyMatrix[city1.getId()][city2.getId()].getDistance(), 0, NOT_CONNECTED);
      adjacencyMatrix[city2.getId()][city1.getId()] = new Route(adjacencyMatrix[city1.getId()][city2.getId()].getDistance(), 0, NOT_CONNECTED);
    }
  }

  public CityNode getCityByUbigeo(String ubigeo) {
    return cities.stream().filter(city -> city.getUbigeo().equals(ubigeo)).findFirst().orElse(null);
  }
  public CityNode getCityByNombre(String nombre) {
    return cities.stream().filter(city -> city.getName().equals(nombre)).findFirst().orElse(null);
  }
}