package model;

import data.Instance;

import java.util.List;

public class LogisticRegression implements Model<Number, Integer> {
    @Override
    public void train(List<Instance<Number, Integer>> instances) {

    }

    @Override
    public List<Integer> test(List<Instance<Number, Integer>> instances) {
        return List.of();
    }
}
