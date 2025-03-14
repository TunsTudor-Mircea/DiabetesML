package evaluation;

import data.Instance;

import java.util.List;

public class F1Score implements EvaluationMeasure<Number, Integer> {
    @Override
    public double evaluate(List<Instance<Number, Integer>> instances, List<Integer> predictions) {
        Precision precisionObj = new Precision();
        Recall recallObj = new Recall();

        double precision = precisionObj.evaluate(instances, predictions);
        double recall = recallObj.evaluate(instances, predictions);

        return 2 * precision * recall / (precision + recall);
    }
}
