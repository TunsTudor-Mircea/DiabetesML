package evaluation;

import data.Instance;

import java.util.List;

public class Accuracy implements EvaluationMeasure<Number, Integer> {
    public double evaluate(List<Instance<Number, Integer>> instances, List<Integer> predictions) {
        int correct = 0;

        for (int i = 0; i < instances.size(); i++) {
            if (instances.get(i).getOutput().equals(predictions.get(i))) {
                correct++;
            }
        }

        return (double) correct / instances.size();
    }
}
