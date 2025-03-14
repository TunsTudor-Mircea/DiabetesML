package evaluation;

import data.Instance;

import java.util.List;

public class Precision implements EvaluationMeasure<Number, Integer> {
    @Override
    public double evaluate(List<Instance<Number, Integer>> instances, List<Integer> predictions) {
        int truePositives = 0;
        int falsePositives = 0;

        for (int i = 0; i < instances.size(); i++) {
            if (predictions.get(i).equals(1)) {
                if (instances.get(i).getOutput().equals(1)) {
                    truePositives++;
                }
                else {
                    falsePositives++;
                }
            }
        }

        return truePositives / (double) (truePositives + falsePositives);
    }
}
