package com.company;

import com.company.GA.GeneticAlgorithm;
import com.company.utils.GraphShortestPath;
import com.company.utils.graph.CitiesGraph;
import com.company.utils.graph.CityNode;
import com.company.utils.graph.FindPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static com.company.utils.Util.euclideanDistance;

public class Main {
  public static Graph map = new Graph();

  public static void main(String[] args) throws IOException {

    List<Node> nodes = new ArrayList<>();
    Node d1 = new Node("010601", -6.39590702, -77.4821999, 0);
    Node d2 = new Node("010201", -5.63906152, -78.53166353, 1);
    Node d3 = new Node("010401", -4.59234702, -77.86447689, 2);
    Node off1 = new Node("010101", -6.22940827, -77.8724339, 3);
    Node off2 = new Node("010301", -5.90432416, -77.79809916, 4);

    nodes.add(d1);
    nodes.add(d2);
    nodes.add(d3);
    nodes.add(off1);
    nodes.add(off2);
    int nodesNumber = nodes.size();
    //adjacent nodes
    Relationship map[][] = {
        {new Relationship(0, 1),
            new Relationship(-1, 2),
            new Relationship(30, 3),
            new Relationship(50, 4),
            new Relationship(10, 5)},
        {new Relationship(-1),
            new Relationship(0, 3),
            new Relationship(-1, 6),
            new Relationship(35, 4),
            new Relationship(15, 7)},
        {new Relationship(30, 2),
            new Relationship(-1, 4),
            new Relationship(0, 2),
            new Relationship(5),
            new Relationship(35)},
        {new Relationship(50),
            new Relationship(35),
            new Relationship(5),
            new Relationship(0),
            new Relationship(20)},
        {new Relationship(10),
            new Relationship(15),
            new Relationship(40),
            new Relationship(20),
            new Relationship(0)},
    
    };

    Set<CityNode> cities = new HashSet<>();
    CityNode a = new CityNode("A");
    CityNode b = new CityNode("B");
    CityNode c = new CityNode("C");
    CityNode d = new CityNode("D");
    CityNode e = new CityNode("E");
    cities.addAll(List.of(a,b,c,d,e));

    CitiesGraph graph = new CitiesGraph(cities);
    graph.connectCities(a, b, 50);
    graph.connectCities(b, c, 30);
    graph.connectCities(c, d, 40);
    graph.connectCities(d, e, 20);
    graph.show();

    FindPath findPath = new FindPath();
    System.out.println(findPath.calculateShortestPath(graph, a, e)); //prints 140 as expected
    
    
//        for(int i=0;i<nodesNumber;i++){
//            for(int j=0;j< nodesNumber;j++) {
//                map[i][j]=new Relationship(nodes.get(i),nodes.get(j));
//            }
//        }


    //maybe for REAL routes we need a graph in linkedList implementation
//        //adjacent nodes of each node bidirectionally
//        map.addEdge(d1,off1);
//        map.addEdge(d1,off2);
//        map.addEdge(d2,off1);
//        map.addEdge(d2,off2);
//        map.addEdge(d3,off1);
//        map.addEdge(d3,off2);
//        map.addEdge(off1,off2);


//    System.out.println(map);
//    GraphShortestPath gsp = new GraphShortestPath(map.length);
//    
//    gsp.algo_dijkstra(map, 0);
//    System.out.println("distancia minima entre 0 y 4 es: " + gsp.minimumDistanceBetweenTwoNodes(map, 0, 4));

//        //parameters
//        final int maxNumberLimaVehicles = 1;
//        final int maxNumberTrujilloVehicles = 1;
//        final int maxNumberArequipaVehicles = 1;
//
//        List<Depot> depots = new ArrayList<>();
//        depots.add(new Depot(d1,"1",40,maxNumberLimaVehicles));
//        depots.add(new Depot(d2,"2",20,maxNumberTrujilloVehicles));
//        depots.add(new Depot(d3,"3",10,maxNumberArequipaVehicles));
//
//
//        List<Order> orders = new ArrayList<>();
//        //orders arrive
//        orders.add(new Order(off1,"1",0,10));
//        orders.add(new Order(off1,"2",0,20));
//        orders.add(new Order(off1,"3",0,15));
//        orders.add(new Order(off1,"4",0,5));
//        orders.add(new Order(off2,"5",0,20));
//
//
//        File file = new File("src/com/company/resources/map01");
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        String line;
//        int index = 0;
//        int depotIndex = 0;
//        int maxVehicles = 0; // m: maximum number of vehicles available in each depot
//        int totalCustomers = 0; // n: total number of customers
//        int depotsCount = 0; // t: number of depots
//        //just reading data
//        while (false &&(line = br.readLine()) != null) {
//            String[] stringLineArr = line.trim().split("\\s+");
//            int[] lineArr = Arrays.stream(stringLineArr).mapToInt(Integer::parseInt).toArray();
//
//            if (index == 0) { // Map info: m n t
////                System.out.println("Map info: " + line);
//                maxVehicles = lineArr[0];
//                totalCustomers = lineArr[1];
//                depotsCount = lineArr[2];
//            } else if (index <= depotsCount) { // Depot info: The next t lines contain, the following information: D Q
////                System.out.println("Depot info: " + line);
//                Depot depot = new Depot(lineArr[0], lineArr[1], maxVehicles);
//                depots.add(depot);
//            } else if (index <= depotsCount + totalCustomers) { // Customer: id, x, y, d, q
////                System.out.println("Customer info: " + line);
//                Order order = new Order(Integer.toString(lineArr[0]), lineArr[1], lineArr[2], lineArr[3], lineArr[4]);
//                orders.add(order);
//            } else if (depotIndex <= depotsCount) { // Depot coordinates: id, x, y
////                System.out.println("Depot location: " + line);
//                Depot depot = depots.get(depotIndex);
//                depot.setId(Integer.toString(lineArr[0]));
//                depot.setCoordinates(lineArr[1], lineArr[2]);
//                depotIndex++;
//            } else {
//                System.out.println("Oh no, I shouldn't be here!");
//            }
//            index++;
//        }
//
//


    //this helps init population of GA
//        assignOrdersToNearestDepot2(orders,depots);

//        GeneticAlgorithm ga = new GeneticAlgorithm(depots);
//        ga.run();


//        System.out.println("Fitness: "+ String.format(Locale.ROOT, "%.2f", ga.getAlphaSolution().getFitness()));
//        System.out.println("Numero de rutas:" + ga.getAlphaSolution().getVehicles().size());
//        int z=0;
//        for ( Vehicle vehicle: ga.getAlphaSolution().getVehicles()){
//            System.out.println("\n" + formatOutputLine(vehicle.getStartDepot().getId(),z+1,vehicle.calculateRouteDuration(),vehicle.getCurrentLoad(),vehicle.getRoute()));
//            z++;
//        }

    
    
    
  }


  public static void assignOrdersToNearestDepot2(List<Order> orders, List<Depot> depots) {
    Depot nearestDepot = null;
    for (Order order : orders) {
      double minimumDistance = Double.MAX_VALUE;
      for (Depot depot : depots) {

        if (true) {
          double distance = euclideanDistance(order.getX(), depot.getX(), order.getY(), depot.getY());
          if (distance < minimumDistance) {
            minimumDistance = distance;
            nearestDepot = depot;
          }
        }
      }

      if (nearestDepot == null) {
        throw new NullPointerException("Nearest Depot is not set");
      }
      nearestDepot.getOrders().add(order);
    }
  }

  public static void assignOrdersToNearestDepot(List<Order> orders, List<Depot> depots) {
    Depot nearestDepot = null;
    for (Order order : orders) {
      double minimumDistance = Double.MAX_VALUE;
      for (Depot depot : depots) {
        double distance = euclideanDistance(order.getX1(), depot.getX1(), order.getY1(), depot.getY1());
        if (distance < minimumDistance) {
          minimumDistance = distance;
          nearestDepot = depot;
        }
      }

      if (nearestDepot == null) {
        throw new NullPointerException("Nearest Depot is not set");
      }
      nearestDepot.getOrders().add(order);
    }
  }

  public static String formatOutputLine(String depotID, int vehicleID, double distance, int demand, List<Order> route) {
    String output = "ID almacen:" + depotID + "  ID vehiculo:" + vehicleID + "  distancia:" + String.format(Locale.ROOT, "%.2f", distance) + "  carga:" + demand + "\n ruta de entrega en oficinas: ";
    for (Order order : route) {
      output += order.getId() + " ";
    }
    return output;
  }

}
