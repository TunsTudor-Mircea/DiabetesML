package model;

import data.Instance;

import java.util.ArrayList;
import java.util.List;

public class LogisticRegression implements Model<Number, Integer> {
    private List<Double> weights;
    private double bias;
    private final double learningRate;
    private final int epochs;

    public LogisticRegression(double learningRate, int epochs) {
        this.learningRate = learningRate;
        this.epochs = epochs;
    }

    private double sigmoid(double z) {
        return 1.0 / (1.0 + Math.exp(-z));
    }

    @Override
    public void train(List<Instance<Number, Integer>> instances) {
        System.out.println("Training Logistic Regression...");
        int featureCount = instances.get(0).getInput().size();
        weights = new ArrayList<>();

        for (int i = 0; i < featureCount; i++) {
            weights.add(0.0);
        }
        bias = 0.0;

        for (int epoch = 0; epoch < epochs; epoch++) {
            for (Instance<Number, Integer> instance : instances) {
                List<Number> features = instance.getInput();
                int label = instance.getOutput();

                double weightedSum = bias;
                for (int i = 0; i < featureCount; i++) {
                    weightedSum += weights.get(i) * features.get(i).doubleValue();
                }

                double prediction = sigmoid(weightedSum);
                double error = label - prediction;

                for (int i = 0; i < featureCount; i++) {
                    weights.set(i, weights.get(i) + learningRate * error * features.get(i).doubleValue());
                }
                bias += learningRate * error;
            }
        }
        System.out.println("Training complete.");
    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        System.out.println("Testing Logistic Regression...");
        List<Integer> predictions = new ArrayList<>();

        for (Instance<Number, Integer> instance : instances) {
            double weightedSum = bias;
            for (int i = 0; i < weights.size(); i++) {
                weightedSum += weights.get(i) * instance.getInput().get(i).doubleValue();
            }
            predictions.add(sigmoid(weightedSum) >= 0.5 ? 1 : 0);
        }
        return predictions;
    }
}
