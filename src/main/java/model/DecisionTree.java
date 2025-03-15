package model;

import data.Instance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class DecisionTree implements Model<Number, Integer> {
    private Node root;

    private static class Node {
        boolean isLeaf;
        int label;
        int featureIndex;
        double threshold;
        Node left;
        Node right;
    }

    @Override
    public void train(List<Instance<Number, Integer>> instances) {
        System.out.println("Training Decision Tree...");
        root = buildTree(instances);
        System.out.println("Training complete.");
    }

    private Node buildTree(List<Instance<Number, Integer>> instances) {
        if (instances.isEmpty()) return null;

        // Check if all labels are the same
        int firstLabel = instances.getFirst().getOutput();
        boolean allSame = instances.stream().allMatch(i -> i.getOutput() == firstLabel);
        if (allSame) {
            Node leaf = new Node();
            leaf.isLeaf = true;
            leaf.label = firstLabel;
            return leaf;
        }

        // Find best split
        int bestFeature = -1;
        double bestThreshold = 0.0;
        double bestGini = Double.MAX_VALUE;

        int featureCount = instances.getFirst().getInput().size();
        for (int i = 0; i < featureCount; i++) {
            double threshold = findBestThreshold(instances, i);
            double gini = calculateGini(instances, i, threshold);
            if (gini < bestGini) {
                bestGini = gini;
                bestFeature = i;
                bestThreshold = threshold;
            }
        }

        // split instances
        List<Instance<Number, Integer>> leftInstances = new ArrayList<>();
        List<Instance<Number, Integer>> rightInstances = new ArrayList<>();
        for (Instance<Number, Integer> instance : instances) {
            if (instance.getInput().get(bestFeature).doubleValue() <= bestThreshold) {
                leftInstances.add(instance);
            } else {
                rightInstances.add(instance);
            }
        }

        // Create node
        Node node = new Node();
        node.featureIndex = bestFeature;
        node.threshold = bestThreshold;
        node.left = buildTree(leftInstances);
        node.right = buildTree(rightInstances);
        return node;
    }

    private double findBestThreshold(List<Instance<Number, Integer>> instances, int featureIndex) {
        return instances.stream().mapToDouble(i -> i.getInput().get(featureIndex).doubleValue()).average().orElse(0.0);
    }

    private double calculateGini(List<Instance<Number, Integer>> instances, int featureIndex, double threshold) {
        List<Instance<Number, Integer>> left = new ArrayList<>();
        List<Instance<Number, Integer>> right = new ArrayList<>();
        for (Instance<Number, Integer> instance : instances) {
            if (instance.getInput().get(featureIndex).doubleValue() <= threshold) {
                left.add(instance);
            } else {
                right.add(instance);
            }
        }
        return (giniImpurity(left) * left.size() + giniImpurity(right) * right.size()) / instances.size();
    }

    private double giniImpurity(List<Instance<Number, Integer>> instances) {
        if (instances.isEmpty()) return 0.0;
        Map<Integer, Integer> labelCounts = new HashMap<>();
        for (Instance<Number, Integer> instance : instances) {
            labelCounts.put(instance.getOutput(), labelCounts.getOrDefault(instance.getOutput(), 0) + 1);
        }
        double impurity = 1.0;
        for (int count : labelCounts.values()) {
            double prob = (double) count / instances.size();
            impurity -= prob * prob;
        }
        return impurity;
    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        System.out.println("Testing Decision Tree...");
        List<Integer> predictions = new ArrayList<>();
        for (Instance<Number, Integer> instance : instances) {
            predictions.add(predict(instance, root));
        }
        return predictions;
    }

    private int predict(Instance<Number, Integer> instance, Node node) {
        if (node.isLeaf) return node.label;
        if (instance.getInput().get(node.featureIndex).doubleValue() <= node.threshold) {
            return predict(instance, node.left);
        } else {
            return predict(instance, node.right);
        }
    }
}
