package com.company.utils;

import com.company.utils.graph.CitiesGraph;
import com.company.utils.graph.CityNode;

import java.util.Set;

public class MockGraph {
      public static  CitiesGraph GRAPH;

  public static void initMockGraph( CityNode ...cities) {
    GRAPH = new CitiesGraph(Set.of(cities));
    GRAPH.connectCities(cities[0], cities[1], 50);
    GRAPH.connectCities(cities[1], cities[2], 30);
    GRAPH.connectCities(cities[2], cities[3], 40);
    GRAPH.connectCities(cities[3], cities[4], 20);
    GRAPH.connectCities(cities[1], cities[5], 10);
    GRAPH.connectCities(cities[5], cities[3], 10);
    GRAPH.show();
  }
  
}
