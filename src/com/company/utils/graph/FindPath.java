package com.company.utils.graph;

import java.util.*;

public class FindPath{

  //map to hold distances of all node from origin. at the end this map should contain
  //the shortest distance between origin (from) to all other nodes
  static Map<CityNode, Integer> distances;

  //using Dijkstra algorithm
  public static int calculateShortestPath(CitiesGraph graph, CityNode from, CityNode to) {

    //a container to hold which cities the algorithm has visited
    Set<CityNode> settledCities = new HashSet<>();
    //a container to hold which cities the algorithm has to visit
    Set<CityNode> unsettledCities = new HashSet<>();
    unsettledCities.add(from);

    //map to hold distances of all node from origin. at the end this map should contain
    //the shortest distance between origin (from) to all other nodes
    distances = new HashMap<>();
    //initialize map with values: 0 distance to origin, infinite distance to all others
    //infinite means no connection between nodes
    for(CityNode city :graph.getCities()){
      int distance = city.equals(from) ? 0 : Integer.MAX_VALUE;
      distances.put(city, distance);
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
        int distanceToCity = graph.getDistanceBetween(city, currentCity);
        if(distanceToCity <= 0) {
          continue;
        }
        if(distances.get(currentCity) + distanceToCity < distances.get(city)){
          //if so, keep the shortest distance found
          distances.put(city,distances.get(currentCity) + distanceToCity);
          //if city has not been visited yet, add it to unsettledCities
          if(! settledCities.contains(city)) {
            unsettledCities.add(city);
          }
        }
      }
    }

    return distances.get(to);
  }

  private static  CityNode getLowestDistanceCity(Set <CityNode> unsettledCities) {

    return unsettledCities.stream()
        .min((c1,c2)-> Integer.compare(distances.get(c1), distances.get(c2)))
        .orElse(null);
  }
}
