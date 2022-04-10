package com.company.GA;
import com.company.Depot;
import com.company.Order;
import com.company.Vehicle;
import com.company.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * n Solutions = populationSize
 */
public class Population {
    private List<Depot> depots;
    private List<Individual> individuals = new ArrayList<>();

    private Individual alphaIndividual; // Best Individual (with best fitness)

    private int generation = 0; // Increment after each loop
    private List<Double> generations = new ArrayList<>();

    private int populationSize;
    private double crossOverRate;
    private double mutationRate;
    private int tournamentSize;
    private int numberOfChildren;
    private int numberOfParentsToSave;
    private int durationPenaltyRate;
    private int loadPenaltyRate;
    private boolean elitism;
    private int k;

    /**
     * Sets parameters
     * Generates initial population which generates n random Solutions. n = populationSize
     */
    public Population(List<Depot> depots,
                      int populationSize,
                      double crossOverRate,
                      double mutationRate,
                      int tournamentSize,
                      int numberOfChildren,
                      int numberOfParentsToSave,
                      int durationPenaltyRate,
                      int loadPenaltyRate,
                      boolean elitism,
                      int k) {
        this.depots = depots;
        this.populationSize = populationSize;
        this.crossOverRate = crossOverRate;
        this.mutationRate = mutationRate;
        this.tournamentSize = tournamentSize;
        this.numberOfChildren = numberOfChildren;
        this.numberOfParentsToSave = numberOfParentsToSave;
        this.durationPenaltyRate = durationPenaltyRate;
        this.loadPenaltyRate = loadPenaltyRate;
        this.elitism = elitism;
        this.k = k;
    }

    /**
     * One generation of Population
     * Loops through one generation of each Individual
     * 0. Init population
     * 1. Selection
     * 2. Crossover
     * 3. Mutation
     * 4. Calculate fitness
     * 5. Filtering
     */
    public void GArunned() {
        if (generation == 0) {
            //0. Init population
            generateInitialPopulation();
            individuals.sort(Comparator.comparingDouble(Individual::getFitness));
        } else {
            List<Individual> children = new ArrayList<>();
            List<Individual> parentsToRemove = new ArrayList<>();
            for (int i = 0; i < numberOfChildren; i++) {
                // 1. Selection
                Individual[] parents = selection();
                // 2. Crossover
                // Parents get to crossover if random is less than crossOverRate
                double random = Util.randomDouble();
                if (random < crossOverRate) {
                    children.addAll(crossOver(parents));
                }

                if (!elitism && children.size() != 0) {
                    parentsToRemove.addAll(List.of(parents[0], parents[1]));
                }
            }

            List<Individual> childrenToAdd = new ArrayList<>();
            for (Individual child : children) {
                //3. Mutation
                double random = Util.randomDouble();
                if (random < mutationRate) {
                    random = Util.randomDouble();

                    Individual mutatedChild;

                    if (random <= 0.33) {
                        mutatedChild = new Individual(depots, durationPenaltyRate, loadPenaltyRate, child.swapMutation());
                    } else if (random <= 0.66) {
                        mutatedChild = new Individual(depots, durationPenaltyRate, loadPenaltyRate, child.swapMutation2());
                    } else {
                        mutatedChild = new Individual(depots, durationPenaltyRate, loadPenaltyRate, child.crossMutation());
                    }
                    childrenToAdd.add(mutatedChild);
                } else {
                    childrenToAdd.add(child);
                }
            }

            if (!elitism) {
                individuals.removeAll(parentsToRemove);
            }
            //4. Calculate fitness
            individuals.sort(Comparator.comparingDouble(Individual::getFitness)); // Sort by fitness
            //5. Filtering
            List<Individual> parentsToSave = new ArrayList<>(individuals.subList(0, numberOfParentsToSave));
            individuals = childrenToAdd;
            individuals.addAll(parentsToSave);
            individuals.sort(Comparator.comparingDouble(Individual::getFitness)); // Sort by fitness
            individuals = individuals.stream().limit(populationSize).collect(Collectors.toList()); // Cut population to population size
        }
        alphaIndividual = individuals.get(0);//the best is the one that has the minimum fitness
        generations.add(getAlphaFitness());
        generation++;
    }

    /**
     * Generates initial population which generates n random Solutions. n = populationSize
     */
    private void generateInitialPopulation() {
        int triesLeft = 1000;
        boolean force = false;

        while (individuals.size() != populationSize) {
            if (triesLeft == 0) {
                force = true;
            }

            Individual individual = new Individual(depots, durationPenaltyRate, loadPenaltyRate);

            boolean successful = individual.generateOptimizedIndividual2(force);
            if (successful) {
                individual.calculateFitness();
                individuals.add(individual);
            } else {
                triesLeft--;
            }
        }

        if (triesLeft == 0) {
            System.out.println("Generated population with constraint break");
        }
    }

    private List<Individual> crossOver(Individual[] parents) { //TODO BCRC Cross Over mentioned in our pdf
        List<Vehicle> solutionVehicles = parents[0].getVehicles();
        int randIndex = Util.randomIndex(solutionVehicles.size());
        Vehicle solutionVehicle = solutionVehicles.get(randIndex);

        List<Vehicle> partnerVehicles = parents[1].getVehicles();
        int randIndex2 = Util.randomIndex(parents[1].getVehicles().size());

        Vehicle partnerVehicle = partnerVehicles.get(randIndex2);
        ArrayList<ArrayList<Order>> partsFromS1 = Util.splitRoute(solutionVehicle.getRoute(), k);
        ArrayList<ArrayList<Order>> partsFromS2 = Util.splitRoute(partnerVehicle.getRoute(), k);

        List<Individual> children = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            List<Vehicle> newVehicles = parents[0].singlePointCrossOver(partsFromS2.get(i));
            List<Vehicle> newVehicles2 = parents[1].singlePointCrossOver(partsFromS1.get(i));
            children.add(new Individual(depots, durationPenaltyRate, loadPenaltyRate, newVehicles));
            children.add(new Individual(depots, durationPenaltyRate, loadPenaltyRate, newVehicles2));
        }

        return children;
    }

    private Individual[] selection() {
//        Individual parent1 = tournament();
        Individual parent1 = rouletteWheel();
        Individual parent2 = parent1;

        while (parent1 == parent2) {
//            parent2 = tournament();
            parent2 = rouletteWheel();
        }

        return new Individual[]{parent1, parent2};
    }

    private Individual rouletteWheel() {
        double totalFitness = getTotalFitness();

        int threshold = Util.randomIndex((int) totalFitness);
        totalFitness = 0.0;

        for (Individual individual : individuals) {
            totalFitness += individual.getFitness();

            if ((int) totalFitness > threshold) {
                return individual;
            }
        }

        return null;
    }

    private Individual tournament() {
        List<Individual> tournamentMembers = new ArrayList<>();

        for (int i = 0; i < tournamentSize; i++) {
            boolean contained = true;
            Individual member = null;
            while (contained) {
                int randomIndex = Util.randomIndex(individuals.size());
                member = individuals.get(randomIndex);
                contained = tournamentMembers.contains(member);
            }
            tournamentMembers.add(member);
        }
        tournamentMembers.sort(Comparator.comparingDouble(Individual::getFitness));
        return tournamentMembers.get(0);
    }

    private double getTotalFitness() {
        double totalFitness = 0.0;

        for (Individual individual : individuals) {
            totalFitness += individual.getFitness();
        }

        return totalFitness;
    }

    public double getAlphaDuration() {
        return alphaIndividual.getDuration();
    }

    public double getAlphaFitness() {
        return alphaIndividual.getFitness();
    }

    public Individual getAlphaIndividual() {
        return alphaIndividual;
    }

    public double getAverageFitness() {
        double totalFitness = getTotalFitness();
        return totalFitness / individuals.size();
    }

    public int getGeneration() {
        return generation;
    }

    public boolean isAlphaValid() {
        return alphaIndividual.isValid();
    }

    public List<Double> getGenerations() {
        return generations;
    }
}
