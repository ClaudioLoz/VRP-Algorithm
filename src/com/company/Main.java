package com.company;

import com.company.GA.GeneticAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.company.utils.Util.euclideanDistance;

public class Main {

    public static void main(String[] args) throws IOException {
         int maxNumberLimaVehicle = 4;
         int maxNumberTrujilloVehicle = 4;
         int maxNumberArequipaVehicle = 4;
         List<Depot> depots = new ArrayList<>();
         List<Order> orders = new ArrayList<>();
         List<Vehicle> vehicles = new ArrayList<>();

        //after
//        depots.add(new Depot(20,20,1,0,80));
//        depots.add(new Depot(30,40,2,0,80));
//        depots.add(new Depot(50,30,3,0,80));
//        depots.add(new Depot(60,50,4,0,80));
        File file = new File("src/com/company/resources/map01");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int index = 0;
        int depotIndex = 0;

        int maxVehicles = 0; // m: maximum number of vehicles available in each depot
        int totalCustomers = 0; // n: total number of customers
        int depotsCount = 0; // t: number of depots

        System.out.println("========= Parsing map file =========");
        //just reading data
        while ((line = br.readLine()) != null) {
            String[] stringLineArr = line.trim().split("\\s+");
            int[] lineArr = Arrays.stream(stringLineArr).mapToInt(Integer::parseInt).toArray();

            if (index == 0) { // Map info: m n t
                System.out.println("Map info: " + line);
                maxVehicles = lineArr[0];
                totalCustomers = lineArr[1];
                depotsCount = lineArr[2];
            } else if (index <= depotsCount) { // Depot info: The next t lines contain, the following information: D Q
                System.out.println("Depot info: " + line);
                Depot depot = new Depot(lineArr[0], lineArr[1], maxVehicles);
                depots.add(depot);
            } else if (index <= depotsCount + totalCustomers) { // Customer: id, x, y, d, q
                System.out.println("Customer info: " + line);
                Order order = new Order(Integer.toString(lineArr[0]), lineArr[1], lineArr[2], lineArr[3], lineArr[4]);
                orders.add(order);
            } else if (depotIndex <= depotsCount) { // Depot coordinates: id, x, y
                System.out.println("Depot location: " + line);
                Depot depot = depots.get(depotIndex);
                depot.setId(Integer.toString(lineArr[0]));
                depot.setCoordinates(lineArr[1], lineArr[2]);
                for (int i = 0; i < maxVehicles; i++) {
                    Vehicle vehicle = new Vehicle(depot);
                    vehicles.add(vehicle);
                }
                depotIndex++;
            } else {
                System.out.println("Oh no, I shouldn't be here!");
            }
            index++;
        }

        //this helps GA
        assignOrdersToNearestDepot(orders,depots);

        GeneticAlgorithm ga = new GeneticAlgorithm(depots);
        ga.run();


        System.out.println("Fitness: "+ String.format(Locale.ROOT, "%.2f", ga.getAlphaSolution().getFitness()));

        int z=0;
        for ( Vehicle vehicle: ga.getAlphaSolution().getVehicles()){

            for(Order order : vehicle.getRoute()){


            }


            z++;
        }

    }

    public static void assignOrdersToNearestDepot(List<Order> orders,List<Depot> depots) {
        Depot nearestDepot = null;
        for (Order order : orders) {
            double minimumDistance = Double.MAX_VALUE;
            for (Depot depot : depots) {
                double distance = euclideanDistance(order.getX(), depot.getX(), order.getY(), depot.getY());
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

    public static String formatOutputLine(int depotID, int vehicleID, double distance, int demand, List<Integer> route) {
        String output = "" + depotID + "  " + vehicleID + "  " + String.format(Locale.ROOT, "%.2f", distance) + "  " + demand + "  0 ";
        for (int customerID : route) {
            output += customerID + " ";
        }
        return output + "0";
    }

}
