package com.company;

import com.company.GA.GeneticAlgorithm;
import com.company.utils.graph.CitiesGraph;
import com.company.utils.graph.CityNode;
import com.company.utils.graph.FindPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static com.company.utils.MockGraph.*;
import static com.company.utils.Util.euclideanDistance;


public class Main {
    private static CitiesGraph graph;
    public static void main(String[] args) throws IOException {
      
      
      List<CityNode> citiesFile = readCitiesFile();
      initCities(citiesFile);
      readRouteFile();
//      GRAPH.show();
//      if (citiesFile.size() > 0) return;
//        List<Order> orders = readOrderFile();
//!    cities.addAll(List.of(a,b,c,d,e,f));
//    cities.addAll(List.of(a,b,c,d,e));

//      initMockGraph(a,b,c,d,e,f);
//      initMockGraph(a,b,c,d,e);
//    System.out.println(FindPath.getNodesBetweenTwoCities(GRAPH,f,c));

//
      //Read txt file
      //GRAPH.show();

      //GRAPH.blockPath(c,d);


      //parameters
//!
      
        final int maxNumberLimaVehicles = 21;
        final int maxNumberTrujilloVehicles = 10;
        final int maxNumberArequipaVehicles = 14;
        CityNode arequipa = GRAPH.getCityByNombre("AREQUIPA");
        CityNode trujillo = GRAPH.getCityByNombre("TRUJILLO");
        CityNode lima = GRAPH.getCityByNombre("LIMA");

        List<Depot> depots = new ArrayList<>();
        depots.add(new Depot("LIMA",1000,maxNumberLimaVehicles,lima));
        depots.add(new Depot("TRUJILLO",1000,maxNumberTrujilloVehicles, trujillo));
        depots.add(new Depot("AREQUIPA",1000,maxNumberArequipaVehicles, arequipa));
//        //LIMA:150101 
//        //TRUJILLO:130101
//        //AREQUIPA:040101 
      
//        //orders arrive
        List<Order> orders = new ArrayList<>();


        orders.add(new Order(10,  GRAPH.getCityByUbigeo("020501")));
        orders.add(new Order(20,  GRAPH.getCityByUbigeo("020501")));
//        orders.add(new Order(15,GRAPH.getCityByUbigeo("020101")));
//!
      //        orders.add(new Order(off1,"4",0,5,f));
//        orders.add(new Order(off2,"5",0,20,cities.get(33)));

//        List<Depot> depots = new ArrayList<>();
//        depots.add(new Depot(d1,"a",100,maxNumberLimaVehicles, cities.get(0)));
//        depots.add(new Depot(d2,"f",100,maxNumberTrujilloVehicles, cities.get(1)));
//        depots.add(new Depot(d3,"e",100,maxNumberArequipaVehicles, cities.get(2)));
//        List<Order> orders = new ArrayList<>();
//        //orders arrive
//        orders.add(new Order(off1,"b",0,10, cities.get(23)));
//        orders.add(new Order(off1,"c",0,20, cities.get(47)));
//        orders.add(new Order(off1,"d",0,15,cities.get(19)));
//        orders.add(new Order(off1,"4",0,5,cities.get(7)));
//        orders.add(new Order(off2,"5",0,20,cities.get(33)));
//      
//!       
//        assignOrdersToNearestDepot2(orders,depots);
//
//        depots.forEach(depot ->{ System.out.println("\nDepot "+depot.getCity().getName()+"\nAssigned Orders: ");
//            depot.getOrders().forEach(order -> System.out.println(order.getCity().getName() + " "));});

      long startTime = System.currentTimeMillis();
      GeneticAlgorithm ga = new GeneticAlgorithm(depots);
        ga.run();
      long endTime = System.currentTimeMillis() - startTime; // tiempo en que se ejecuta su for 
      System.out.println("Tiempo de ejecución: " + endTime/1000 + " segundos");
//      System.out.println(FindPath.calculateShortestPath(GRAPH, trujillo, GRAPH.getCityByUbigeo("020501")));
//      System.out.println(FindPath.getNodesBetweenTwoCities(GRAPH, trujillo, GRAPH.getCityByUbigeo("020501")));
//
        System.out.println("Fitness: "+ String.format(Locale.ROOT, "%.2f", ga.getAlphaSolution().getFitness()));
        System.out.println("Numero de rutas:" + ga.getAlphaSolution().getVehicles().size());
        int z=0;
        for ( Vehicle vehicle: ga.getAlphaSolution().getVehicles()){
          if(vehicle.calculateRouteDuration()<=0)
            continue;
            System.out.println(vehicle);
        }
  
    }

  private static List<Order> readOrderFile() {
    File file = new File("D:/JavaProjects/VRP-Algorithm/src/com/company/resources/inf226.ventas202205.txt");
    List<Order> orders = new ArrayList<>();
    try {
      String line;
      String[] splittedLine;
      BufferedReader br = new BufferedReader(new FileReader(file));
      Scanner scanner;
      while ((line = br.readLine()) != null) {
        splittedLine = line.split(",");
        int demand = Integer.parseInt(splittedLine[2].trim());
        splittedLine = splittedLine[1].trim().split(" =>  ");
        String startDeportUbigeo = splittedLine[1];
        orders.add(new Order(demand, GRAPH.getCityByUbigeo(startDeportUbigeo)));
      }
    }
    catch (Exception e){
      e.printStackTrace();
    }
    return orders;
  }


  private static List<CityNode> readCitiesFile() {
    File file = new File("D:/JavaProjects/VRP-Algorithm/src/com/company/resources/inf226.oficinas.txt");
    List<CityNode> cityNodes = new ArrayList<>();
    try {
      String line;
      String[] splittedLine;
      BufferedReader br = new BufferedReader(new FileReader(file));
      Scanner scanner;
      while ((line = br.readLine()) != null) {
        splittedLine = line.split(",");
        cityNodes.add(new CityNode(Double.parseDouble(splittedLine[3]), Double.parseDouble(splittedLine[4]), splittedLine[0], splittedLine[1], splittedLine[5], splittedLine[2]));
      }
    } catch (Exception e) {
      System.out.println("Error reading file");
      e.printStackTrace();
    }
    return cityNodes;
  }

  private static void readRouteFile() {
    File file = new File("D:/JavaProjects/VRP-Algorithm/src/com/company/resources/inf226.tramos.v.2.0.txt");
    try {
      String line;
      String[] splittedLine;
      BufferedReader br = new BufferedReader(new FileReader(file));
      Scanner scanner;
      while ((line = br.readLine()) != null) {
        splittedLine = line.trim().split(" => ");
        CityNode start = GRAPH.getCityByUbigeo(splittedLine[0]);
        CityNode end = GRAPH.getCityByUbigeo(splittedLine[1]);
        //calcular distancia entre ciudades
        //obtener velocidad segun región
        GRAPH.connectCities(start, end);
      }
    } catch (Exception e) {
      System.out.println("Error reading file");
      e.printStackTrace();
    }
  }

  private static List<CityNode> readFile() {
    File file = new File("D:/JavaProjects/VRP-Algorithm/src/com/company/resources/matriz.txt");
    List<CityNode> cityNodes = new ArrayList<>();
    try {
      String str;
      BufferedReader br = new BufferedReader(new FileReader(file));
      Scanner scanner;
      List<Double> val = new ArrayList<>();
      int counter = 0;
      while((str=br.readLine())!=null){
        scanner= new Scanner(str);
        while(scanner.hasNext()){
          Double readValue = scanner.nextDouble();
          val.add(readValue);
        }
        if(counter==0) {
          createCityList(cityNodes, val.size());
          initCities(cityNodes);
        }
        assignDistanceBetweenNodes(cityNodes,val,counter);
        val.clear();
        counter++;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return cityNodes;
  }

  private static void createCityList(List<CityNode> cityNodes, int size) {
      for(int i=0;i<size;i++){
        cityNodes.add(new CityNode(Integer.toString(i)));
      }
  }


  public static void assignOrdersToNearestDepot2(List<Order> orders,List<Depot> depots) {
        Depot nearestDepot = null;
        for (Order order : orders) {
            double minimumDistance = Double.MAX_VALUE;
            for (Depot depot : depots) {
                double distance = FindPath.calculateShortestPath(GRAPH, depot.getCity(), order.getCity());
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

    public static void assignOrdersToNearestDepot(List<Order> orders,List<Depot> depots) {
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

        return "output";
    }

}
