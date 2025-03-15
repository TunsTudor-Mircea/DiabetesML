package model;

import data.Instance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NaiveBayes implements Model<Number, Integer> {
    private Map<Integer, Double> classProbabilities;
    private Map<Integer, Map<Integer, Double>> featureProbabilities;
    private int featureCount;

    @Override
    public void train(List<Instance<Number, Integer>> instances) {
        System.out.println("Training Naive Bayes...");
        classProbabilities = new HashMap<>();
        featureProbabilities = new HashMap<>();
        featureCount = instances.getFirst().getInput().size();

        Map<Integer, Integer> classCounts = new HashMap<>();
        Map<Integer, Map<Integer, Double>> featureSums = new HashMap<>();
        int totalInstances = instances.size();

        // count occurrences
        for (Instance<Number, Integer> instance : instances) {
            int label = instance.getOutput();
            classCounts.put(label, classCounts.getOrDefault(label, 0) + 1);

            featureSums.putIfAbsent(label, new HashMap<>());

            for (int i = 0; i < featureCount; i++) {
                double featureValue = instance.getInput().get(i).doubleValue();
                featureSums.get(label).put(i, featureSums.get(label).getOrDefault(i, 0.0) + featureValue);
            }
        }

        // compute probabilities
        for (Map.Entry<Integer, Integer> entry : classCounts.entrySet()) {
            int label = entry.getKey();
            int count = entry.getValue();
            classProbabilities.put(label, (double) count / totalInstances);

            featureProbabilities.put(label, new HashMap<>());
            for (int i = 0; i < featureCount; i++) {
                double featureSum = featureSums.get(label).getOrDefault(i, 0.0);
                featureProbabilities.get(label).put(i, (featureSum + 1.0) / (count + 2.0)); // Laplace smoothing
            }
        }

        System.out.println("Training complete.");
    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        System.out.println("Testing Naive Bayes...");

        return instances.stream().map(instance -> {
            double maxProbability = Double.NEGATIVE_INFINITY;
            int bestLabel = -1;

            for (int label : classProbabilities.keySet()) {
                double probability = Math.log(classProbabilities.get(label));

                for (int i = 0; i < featureCount; i++) {
                    double featureValue = instance.getInput().get(i).doubleValue();
                    probability += featureValue * Math.log(featureProbabilities.get(label).get(i));
                }

                if (probability > maxProbability) {
                    maxProbability = probability;
                    bestLabel = label;
                }
            }
            return bestLabel;
        }).toList();
    }
}
