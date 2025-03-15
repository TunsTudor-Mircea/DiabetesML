package model;

import data.Instance;

import java.util.ArrayList;
import java.util.List;

public class Perceptron implements Model<Number, Integer> {
    private List<Double> weights;
    private double bias;
    private final double learningRate;
    private final int epochs;

    public Perceptron(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    @Override
    public void train(List<Instance<Number, Integer>> instances) {
        System.out.println("Training Perceptron...");
        int featureCount = instances.getFirst().getInput().size();
        weights = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            weights.add(0.0);
        }
        bias = 0.0;

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Instance<Number, Integer> instance : instances) {
                List<Number> features = instance.getInput();
                int label = instance.getOutput() == 1 ? 1 : -1;

                double weightedSum = bias;
                for (int i = 0; i < featureCount; i++) {
                    weightedSum += weights.get(i) * features.get(i).doubleValue();
                }

                int prediction = weightedSum >= 0 ? 1 : -1;

                if (prediction != label) {
                    for (int i = 0; i < featureCount; i++) {
                        weights.set(i, weights.get(i) + learningRate * label * features.get(i).doubleValue());
                    }
                    bias += learningRate * label;
                }
            }
        }
        System.out.println("Training complete.");
    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        System.out.println("Testing Perceptron...");
        List<Integer> predictions = new ArrayList<>();

        for (Instance<Number, Integer> instance : instances) {
            double weightedSum = bias;
            for (int i = 0; i < weights.size(); i++) {
                weightedSum += weights.get(i) * instance.getInput().get(i).doubleValue();
            }
            predictions.add(weightedSum >= 0 ? 1 : 0);
        }
        return predictions;
    }
}
