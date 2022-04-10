package com.company.GA;

import com.company.Depot;
import com.company.Order;
import com.company.Vehicle;
import com.company.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {

    private List<Depot> depots;
    private List<Vehicle> vehicles;
    private int durationPenaltyRate;
    private int loadPenaltyRate;
    private double fitness;
    private boolean isValid;


    public Individual(List<Depot> depots, int durationPenaltyRate, int loadPenaltyRate) {
        this.depots = depots;
        this.durationPenaltyRate = durationPenaltyRate;
        this.loadPenaltyRate = loadPenaltyRate;
        vehicles = new ArrayList<>();
        calculateFitness();
    }

    public Individual(List<Depot> depots, int durationPenaltyRate, int loadPenaltyRate, List<Vehicle> vehicles) {
        this.depots = depots;
        this.durationPenaltyRate = durationPenaltyRate;
        this.loadPenaltyRate = loadPenaltyRate;
        this.vehicles = new ArrayList<>(vehicles);
        calculateFitness();
    }

    public boolean generateOptimizedIndividual(boolean force) {
        for (Depot depot : depots) {
            List<Vehicle> depotVehicles = createDepotVehicles(depot);

            List<Order> depotOrders = new ArrayList<>(depot.getOrders()); // Current depot's orders
            Collections.shuffle(depotOrders);

            for (Order order : depotOrders) {
                boolean orderAdded = false;
                int triesLeft = 100;
                while (!orderAdded && triesLeft > 0) {

                    double minDuration = Double.MAX_VALUE;
                    int minRouteIndex = -1;
                    Vehicle minVehicle = null;

                    Collections.shuffle(depotVehicles);
                    for (Vehicle vehicle : depotVehicles) {
                        double tempMinDuration = Double.MAX_VALUE;
                        int tempRouteIndex = -1;

                        if (vehicle.getCurrentLoad() + order.getLoadDemand() <= depot.getMaxLoad()) {
                            if (vehicle.getRoute().size() == 0) {
                                tempMinDuration = vehicle.calculateRouteDurationIfOrderAdded(0, order);
                                tempRouteIndex = 0;
                            } else {
                                for (int i = 0; i < vehicle.getRoute().size(); i++) {
                                    double tempDuration = vehicle.calculateRouteDurationIfOrderAdded(i, order);

                                    if (tempDuration < tempMinDuration) {
                                        tempMinDuration = tempDuration;
                                        tempRouteIndex = i;
                                    }
                                }
                            }
                        }

                        if (tempMinDuration < minDuration) {
                            minDuration = tempMinDuration;
                            minRouteIndex = tempRouteIndex;
                            minVehicle = vehicle;
                        }
                    }

                    if (minVehicle == null) {
                        triesLeft--;
                    } else {
                        minVehicle.addOrderToRoute(minRouteIndex, order);

                        if (depot.getMaxDuration() != 0.0 && minVehicle.calculateRouteDuration() > depot.getMaxDuration() && !force) {
                            minVehicle.removeOrderFromRoute(order);
                            triesLeft--;
                        } else {
                            orderAdded = true;
                        }
                    }
                }
                if (triesLeft == 0 && !force) { // Giving up generating this initial isValid
                    return false;
                }
            }
            this.vehicles.addAll(depotVehicles);
        }

        return true;
    }

    public boolean generateOptimizedIndividual2(boolean force) {
        for (Depot depot : depots) {
            List<Vehicle> depotVehicles = createDepotVehicles(depot);
            List<Order> depotOrders = depot.getOrders(); // Current nearest orders
            Collections.shuffle(depotOrders);

            int triesLeft = 100;
            for (Order order : depotOrders) { // Assign order to random vehicle
                boolean orderAdded = false;
                while (!orderAdded && triesLeft > 0) {
                    int randomIndex = Util.randomIndex(depotVehicles.size()); // Random vehicle index
                    Vehicle randomVehicle = depotVehicles.get(randomIndex);

                    orderAdded = randomVehicle.smartAddOrderToRoute(order, force);

                    if (!orderAdded) {
                        triesLeft--;
                    }
                }

                if (triesLeft == 0) { // Giving up generating, logistic collapse ... maybe needs improvement but for while we dgfk
                    return false;
                }
            }

            this.vehicles.addAll(depotVehicles);
        }

        return true;
    }

    public boolean generateRandomIndividual() {
        for (Depot depot : depots) {
            List<Vehicle> depotVehicles = createDepotVehicles(depot);
            List<Order> depotOrders = depot.getOrders(); // Current depot's orders
            Collections.shuffle(depotOrders);

            for (Order order : depotOrders) { // Assign order to random vehicle
                boolean orderAdded = false;
                int randomIndex = Util.randomIndex(depotVehicles.size()); // Random vehicle index
                Vehicle randomVehicle = depotVehicles.get(randomIndex);

                while (!orderAdded) {
                    orderAdded = randomVehicle.addOrderToRoute(order);
                }
            }

            this.vehicles.addAll(depotVehicles);
        }
        return true;
    }

    /**
     * Performs n random mutations on the vehicles based on the mutationRate
     * Mutation is only executed if random < crossOverRate
     */
    public List<Vehicle> swapMutation() {
        // Copy of vehicles
        List<Vehicle> newVehicles = deepCopyVehicles();

        int randomIndex = Util.randomIndex(vehicles.size());
        Vehicle vehicle = vehicles.get(randomIndex);
        List<Order> newRoute = vehicle.swapMutate();
        Vehicle newVehicle = new Vehicle(vehicle.getStartDepot(), newRoute);
        newVehicles.remove(vehicle);
        newVehicles.add(newVehicle);
        return newVehicles;
    }

    public List<Vehicle> swapMutation2() {
        // Copy of vehicles
        List<Vehicle> newVehicles = deepCopyVehicles();

        int randomIndex1 = Util.randomIndex(newVehicles.size());
        int randomIndex2 = Util.randomIndex(newVehicles.size());
        Vehicle vehicle1 = newVehicles.get(randomIndex1);
        Vehicle vehicle2 = newVehicles.get(randomIndex2);
        randomIndex1 = Util.randomIndex(vehicle1.getRoute().size());
        randomIndex2 = Util.randomIndex(vehicle2.getRoute().size());

        Order order1 = null;
        Order order2 = null;

        if (vehicle1.getRoute().size() != 0) {
            randomIndex1 = Util.randomIndex(vehicle1.getRoute().size());
            order1 = vehicle1.getRoute().get(randomIndex1);
        }

        if (vehicle2.getRoute().size() != 0) {
            randomIndex2 = Util.randomIndex(vehicle2.getRoute().size());
            order2 = vehicle2.getRoute().get(randomIndex2);
        }

        if (order1 != null) {
            vehicle1.removeOrderFromRoute(order1);
            vehicle2.addOrderToRoute(randomIndex2, order1);
        }

        if (order2 != null) {
            vehicle2.removeOrderFromRoute(order2);
            vehicle1.addOrderToRoute(randomIndex1, order2);
        }

        return newVehicles;
    }

    public List<Vehicle> crossMutation() {
        // Copy of vehicles
        List<Vehicle> newVehicles = deepCopyVehicles();

        // Pick two random vehicles
        int randomIndex1 = Util.randomIndex(vehicles.size());
        int randomIndex2 = Util.randomIndex(vehicles.size());

        Vehicle randomVehicle1 = vehicles.get(randomIndex1).clone();
        Vehicle randomVehicle2 = vehicles.get(randomIndex2).clone();

        // Remove the old vehicles
        newVehicles.remove(randomVehicle1);
        newVehicles.remove(randomVehicle2);

        // Mutate the two vehicles and add them to newVehicles
        List<Order>[] mutatedRoutes = randomVehicle1.crossMutate(randomVehicle2.getRoute());
        newVehicles.add(new Vehicle(randomVehicle1.getStartDepot(), randomVehicle2.getEndDepot(), mutatedRoutes[0]));
        newVehicles.add(new Vehicle(randomVehicle2.getStartDepot(), randomVehicle1.getEndDepot(), mutatedRoutes[1]));
        return newVehicles;
    }

    private List<Vehicle> createDepotVehicles(Depot depot) {
        List<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < depot.getMaxVehicles(); i++) {
            Vehicle v = new Vehicle(depot);
            vehicles.add(v);
        }
        return vehicles;
    }

    public void calculateFitness() {
        double calculatedFitness = 0.0;
        isValid = true;

        for (Vehicle vehicle : vehicles) {
            double penalty = 0;

            double maxDuration = vehicle.getStartDepot().getMaxDuration();// TODO should depend on region and have to be general ...
            double duration = vehicle.calculateRouteDuration();
            if (maxDuration != 0 && duration > maxDuration) {
                penalty += ((duration - maxDuration) * durationPenaltyRate);
            }

            double maxLoad = vehicle.getStartDepot().getMaxLoad(); //TODO should depend on vehicle (in the constraint too)...
            double load = vehicle.getCurrentLoad();
            if (maxLoad != 0 && load > maxLoad) {
                penalty += ((load - maxLoad) * loadPenaltyRate);
            }

            if (penalty > 0) {
                isValid = false;
            }

            calculatedFitness += (duration + penalty);
        }

        this.fitness = calculatedFitness;
    }

    public double getFitness() {
        return fitness;
    }

    public double getDuration() {
        double duration = 0.0;

        for (Vehicle vehicle : vehicles) {
            duration += vehicle.calculateRouteDuration();
        }

        return duration;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Vehicle> singlePointCrossOver(List<Order> otherRoute) {
        if (vehicles == null) {
            throw new NullPointerException("No vehicles in solution");
        } else if (otherRoute.size() == 0) {
            return vehicles;
        }

        List<Vehicle> newVehicles = deepCopyVehicles();

        // Remove route from otherRoute
        removeRouteFromVehicles(newVehicles, otherRoute);

        // Rull gjennom alle ruter og regn ut diff i fitness på alle mulige steder
        double minFitnessIfAdded = Double.MAX_VALUE;
        Vehicle minVehicle = null;
        int minIndex = -1;

        for (Vehicle vehicle : newVehicles) {
            double fitnessIfAdded;

            if (vehicle.getRoute().size() == 0) {
                fitnessIfAdded = calculateFitnessIfRouteAdded(newVehicles, vehicle, 0, otherRoute);

                minFitnessIfAdded = fitnessIfAdded;
                minVehicle = vehicle;
                minIndex = 0;
            } else {
                for (int routeIndex = 0; routeIndex < vehicle.getRoute().size(); routeIndex++) {
                    fitnessIfAdded = calculateFitnessIfRouteAdded(newVehicles, vehicle, routeIndex, otherRoute);

                    if (fitnessIfAdded < minFitnessIfAdded) {
                        minFitnessIfAdded = fitnessIfAdded;
                        minVehicle = vehicle;
                        minIndex = routeIndex;
                    }
                }
            }

            setBestEndDepot(vehicle);
        }

        if (minVehicle == null) {
            throw new Error("MinVehicle is null");
        } else {
            minVehicle.addOtherRouteToRoute(minIndex, otherRoute);
        }

        return newVehicles;
    }

    private List<Vehicle> deepCopyVehicles() {
        // Creating a deep copy of vehicles
        List<Vehicle> newVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            newVehicles.add(vehicle.clone());
        }
        return newVehicles;
    }

    private void removeRouteFromVehicles(List<Vehicle> newVehicles, List<Order> routeToRemove) {
        for (Vehicle vehicle : newVehicles) {
            List<Order> routeCopy = new ArrayList<>(vehicle.getRoute()); // Copy to avoid error when removing while looping
            for (Order order : routeCopy) {
                if (routeToRemove.contains(order)) {
                    vehicle.removeOrderFromRoute(order);
                }
            }
        }
    }

    private void setBestEndDepot(Vehicle vehicle) {
        if (vehicle.getRoute().size() > 0) {
            double currentMinDistance = Double.MAX_VALUE;
            Depot currentBestEndDepot = null;
            for (Depot depot : depots) {
                double distance = depot.distance(vehicle.getRoute().get(vehicle.getRoute().size() - 1));
                if (distance < currentMinDistance) {
                    currentMinDistance = distance;
                    currentBestEndDepot = depot;
                }
            }
            vehicle.setEndDepot(currentBestEndDepot);
        }
    }

    private double calculateFitnessIfRouteAdded(List<Vehicle> vehicles, Vehicle vehicle, int addIndex, List<Order> routeToAdd) {
        List<Vehicle> originalVehiclesCopy = this.vehicles;
        double originalFitness = fitness;
        this.vehicles = vehicles;
        vehicle.addOtherRouteToRoute(addIndex, routeToAdd);
        calculateFitness();
        double newFitness = fitness;
        vehicle.removeRouteFromRoute(routeToAdd);
        this.fitness = originalFitness;
        this.vehicles = originalVehiclesCopy;
        return newFitness;
    }

    private double calculateFitnessIfOrderAdded(List<Vehicle> vehicles, Vehicle vehicle, int addIndex, Order orderToAdd) {
        List<Vehicle> originalVehiclesCopy = this.vehicles;
        double originalFitness = fitness;
        this.vehicles = vehicles;
        vehicle.addOrderToRoute(addIndex, orderToAdd);
        calculateFitness();
        double newFitness = fitness;
        vehicle.removeOrderFromRoute(orderToAdd);
        this.fitness = originalFitness;
        this.vehicles = originalVehiclesCopy;
        return newFitness;
    }

    public boolean isValid() {
        return isValid;
    }
}
