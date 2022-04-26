package com.company.utils.graph;

import java.util.*;

public class FindPath{

  //map to hold distances of all node from origin. at the end this map should contain
  //the shortest distance between origin (from) to all other nodes
  static Map<CityNode, Double> timeMap;
  static Map<CityNode, List<CityNode>> nodeMap;

  //using Dijkstra algorithm
  public static Double calculateShortestPath(CitiesGraph graph, CityNode from, CityNode to) {

    //a container to hold which cities the algorithm has visited
    Set<CityNode> settledCities = new HashSet<>();
    //a container to hold which cities the algorithm has to visit
    Set<CityNode> unsettledCities = new HashSet<>();
    unsettledCities.add(from);

    //map to hold distances of all node from origin. at the end this map should contain
    //the shortest distance between origin (from) to all other nodes
    timeMap = new HashMap<>();
    //initialize map with values: 0 distance to origin, infinite distance to all others
    //infinite means no connection between nodes
    for(CityNode city :graph.getCities()){
      double distance = city.equals(from) ? 0 : Double.MAX_VALUE;
      timeMap.put(city, distance);
    }

    while (unsettledCities.size() != 0) {
      //get the unvisited city with the lowest distance
      CityNode currentCity = getLowestDistanceCity(unsettledCities);
      //remove from unvisited, add to visited
      unsettledCities.remove(currentCity); settledCities.add(currentCity);

      Collection<CityNode> connectedCities = graph.getCitiesConnectedTo(currentCity);
      //iterate over connected city to update distance to each
      for( CityNode city : connectedCities){
        //check if new distance is shorted than the previously found distance
        double timeCity = graph.getTimeBetween(city, currentCity);
        if(timeCity <= 0) {
          continue;
        }
        if(timeMap.get(currentCity) + timeCity < timeMap.get(city)){
          //if so, keep the shortest distance found
          timeMap.put(city, timeMap.get(currentCity) + timeCity);
          //if city has not been visited yet, add it to unsettledCities
          if(! settledCities.contains(city)) {
            unsettledCities.add(city);
          }
        }
      }
    }

    return timeMap.get(to);
  }
  
  
  public static List<CityNode> getNodesBetweenTwoCities(CitiesGraph graph, CityNode from, CityNode to) {
    Set<CityNode> settledCities = new HashSet<>();
    Set<CityNode> unsettledCities = new HashSet<>();
    unsettledCities.add(from);
    timeMap = new HashMap<>();
    for(CityNode city :graph.getCities()){
      double time = city.equals(from) ? 0 : Double.MAX_VALUE;
      nodeMap.put(city, new ArrayList<>());
      timeMap.put(city, time);
    }

    while (unsettledCities.size() != 0) {
      CityNode currentCity = getLowestDistanceCity(unsettledCities);
      unsettledCities.remove(currentCity); settledCities.add(currentCity);
      Collection<CityNode> connectedCities = graph.getCitiesConnectedTo(currentCity);
      for( CityNode city : connectedCities){
        double timeCity = graph.getTimeBetween(city, currentCity);
        if(timeCity <= 0) {
          continue;
        }
        if(timeMap.get(currentCity) + timeCity < timeMap.get(city)){
          nodeMap.get(city).add(currentCity);
          timeMap.put(city, timeMap.get(currentCity) + timeCity);
          if(! settledCities.contains(city)) {
            unsettledCities.add(city);
          }
        }
      }
    }

    return nodeMap.get(to);
  }

  //en realidad saca el nodo con menor tiempo de separación
  private static  CityNode getLowestDistanceCity(Set <CityNode> unsettledCities) {

    return unsettledCities.stream()
        .min((c1,c2)-> Double.compare(timeMap.get(c1), timeMap.get(c2)))
        .orElse(null);
  }
}
