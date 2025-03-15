package evaluation;

import data.Instance;

import java.util.List;

public class Recall implements EvaluationMeasure<Number, Integer> {
    @Override
    public double evaluate(List<Instance<Number, Integer>> instances, List<Integer> predictions) {
        int truePositives = 0;
        int falseNegative = 0;

        for (int i = 0; i < instances.size(); i++) {
            if (predictions.get(i) == 1) {
                if (instances.get(i).getOutput().equals(predictions.get(i))) {
                    truePositives++;
                }
            } else {
                if (instances.get(i).getOutput().equals(1)) {
                    falseNegative++;
                }
            }
        }

        // avoid division by zero
        if (truePositives + falseNegative == 0) {
            return 1.0;
        }

        return (double) truePositives / (double) (truePositives + falseNegative);
    }
}
