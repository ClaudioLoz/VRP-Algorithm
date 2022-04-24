package com.company.utils;

import com.company.Route;
import com.company.utils.graph.CitiesGraph;
import com.company.utils.graph.CityNode;

import java.util.Set;

public class MockGraph {
      public static  CitiesGraph GRAPH;

  public static void initMockGraph( CityNode ...cities) {
    GRAPH = new CitiesGraph(Set.of(cities));
    GRAPH.connectCities(cities[0], cities[1], new Route(50,2));
    GRAPH.connectCities(cities[1], cities[2], new Route(30,2));
    GRAPH.connectCities(cities[2], cities[3], new Route(40,10));
    GRAPH.connectCities(cities[3], cities[4], new Route(20,2));
    GRAPH.connectCities(cities[1], cities[5], new Route(10,2));
    GRAPH.connectCities(cities[5], cities[3], new Route(10,3));
    GRAPH.show();
  }
  
}
