package com.company.GA;

import com.company.Depot;

import java.util.List;

public class GeneticAlgorithm {
    // Parameters
    private final int maxGenerations = 10;
    private final int populationSize = 75;
    private final double crossOverRate = 0.8; // 80%-95%
    private final double mutationRate = 0.03; // 0.5%-1%.
    private final int tournamentSize = 3; // Number of members in tournament selection
    private final int numberOfChildren = populationSize/3; // Rate of children to produce each generation
    private final int numberOfParentsToSave = populationSize/20; // Number of parents to save in filtering
    private final int durationPenaltyRate = 20; // Penalty for over duration
    private final int loadPenaltyRate = 20; // Penalty for over load
    private final boolean elitism = true; // Elitism keeps the best parents from last generation
    private final int k = 3; // Number of splits in parents before doing crossOver

    private Population population;

    public GeneticAlgorithm(List<Depot> depots) {
        population = new Population(depots,
                populationSize,
                crossOverRate,
                mutationRate,
                tournamentSize,
                numberOfChildren,
                numberOfParentsToSave,
                durationPenaltyRate,
                loadPenaltyRate,
                elitism,
                k);
    }


    public void run() {
        for (int i=1;i<=maxGenerations;i++) population.GArunned();
    }

    public double getAlphaDuration() {
        return population.getAlphaDuration();
    }

    public double getAlphaFitness() {
        return population.getAlphaFitness();
    }

    public boolean isAlphaValid() {
        return population.isAlphaValid();
    }

    public double getAverageFitness() { return population.getAverageFitness(); }

    /**
     * Get best Individual (Individual with best fitness) of Population
     */
    public Individual getAlphaSolution() {
        return population.getAlphaIndividual();
    }

    public int getGeneration() {
        return population.getGeneration();
    }

}
