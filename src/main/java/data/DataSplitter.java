package data;

import domain.Instance;

import java.util.Collections;
import java.util.List;

public class DataSplitter {
    public static void splitData(
            List<Instance<Double, Integer>> instances,
            double trainPercentage,
            List<Instance<Double, Integer>> trainSet,
            List<Instance<Double, Integer>> testSet) {

        // Shuffle the dataset to randomize the split
        Collections.shuffle(instances);

        // Calculate the number of instances to include in the training set
        int trainSize = (int) (instances.size() * trainPercentage);

        // Split into training and testing sets
        trainSet.addAll(instances.subList(0, trainSize));
        testSet.addAll(instances.subList(trainSize, instances.size()));
    }
}
