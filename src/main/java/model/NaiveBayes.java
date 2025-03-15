package model;

import data.Instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes implements Model<Number, Integer> {
    private Map<Integer, Double> classPriorProbabilities = new HashMap<>();
    private Map<Integer, double[]> featureMeans = new HashMap<>();
    private Map<Integer, double[]> featureVariances = new HashMap<>();
    private int numFeatures;


    @Override
    public void train(List<Instance<Number, Integer>> instances) {

    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        return List.of();
    }

    private double gaussianProbability(double x, double mean, double variance) {
        if (variance == 0) variance = 1e-6; // avoid division by zero

        double exponent = Math.exp(-Math.pow(x - mean, 2) / (2 * variance));

        return (1 / Math.sqrt(2 * Math.PI * variance)) * exponent;
    }
}
