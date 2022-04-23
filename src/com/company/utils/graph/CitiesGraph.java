package com.company.utils.graph;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public class CitiesGraph{

  //use set which prevents duplicate entries
  private final Set<CityNode> cities;
  private final int[][] adjacencyMatrix;
  private static final int NOT_CONNECTED = -1;

  public  CitiesGraph(Set<CityNode> cities) {
    this.cities = cities;
    adjacencyMatrix = new int[cities.size()][cities.size()];
    //initialize matrix
    for(int row = 0; row < adjacencyMatrix.length ; row++){
      for(int col = 0; col < adjacencyMatrix[row].length ; col++){
        adjacencyMatrix[row][col] = row == col ? 0 : NOT_CONNECTED ;
      }
    }
  }

  public void connectCities(CityNode city1, CityNode city2, int distance) {
    //assuming undirected graph
    adjacencyMatrix[city1.getId()][city2.getId()] = distance;
    adjacencyMatrix[city2.getId()][city1.getId()] = distance;
  }

  public int getDistanceBetween(CityNode city1, CityNode city2) {

    return adjacencyMatrix[city1.getId()][city2.getId()];
  }

  public Collection<CityNode> getCitiesConnectedTo(CityNode city) {

    Collection<CityNode> connectedCities = new ArrayList<>();
    //iterate over row representing city's connections
    int column = 0;
    for(int distance : adjacencyMatrix[city.getId()]){
      if(distance != NOT_CONNECTED && distance > 0) {
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
    for(int[] row : adjacencyMatrix){
      System.out.println(Arrays.toString(row));
    }
  }

  //get a copy of cities list
  public Collection<CityNode> getCities(){
    return new ArrayList<>(cities);
  }
}