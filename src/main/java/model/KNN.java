package model;

import data.Instance;
import utils.Distance;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class KNN implements Model<Number, Integer>{
    private final int k;
    private final Distance distance;

    private List<Instance<Number, Integer>> trainingInstances;


    public KNN(int k, Distance.DistanceType distanceType) {
        this.k = k;
        distance = new Distance(distanceType);
    }

    @Override
    public void train(List<Instance<Number, Integer>> instances) {
        System.out.println("Training KNN...");
        // store testing data
        this.trainingInstances = new ArrayList<>(instances);

        System.out.println("Training complete. " + instances.size() + " instances stored.");
    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        List<Integer> testResults = new ArrayList<>();

        for (Instance<Number, Integer> instance : instances) {
            // priority queue to store the k closest training instances
            PriorityQueue<Instance<Number, Integer>> neighborQueue = new PriorityQueue<>((e1, e2) ->
                    Double.compare(distance.calculateDistance(instance, e2), distance.calculateDistance(instance, e1))
            );

            for (Instance<Number, Integer> neighborInstance : this.trainingInstances) {
                double currentDistance = distance.calculateDistance(instance, neighborInstance);

                if (neighborQueue.size() < k) {
                    neighborQueue.add(neighborInstance);
                    continue;
                }

                Instance<Number, Integer> farthestNeighbor = neighborQueue.peek();
                double farthestDistance = distance.calculateDistance(instance, farthestNeighbor);

                if (currentDistance < farthestDistance) {
                    neighborQueue.poll();
                    neighborQueue.add(neighborInstance);
                }
            }

            // store results
            int truePredictions = 0;
            int falsePredictions = 0;

            for (Instance<Number, Integer> neighbour : neighborQueue) {
                if (neighbour.getOutput() == 1) {
                    truePredictions++;
                } else {
                    falsePredictions++;
                }
            }

            if (truePredictions > falsePredictions) {
                testResults.add(1);
            } else {
                testResults.add(0);
            }
        }

        return testResults;
    }
}
