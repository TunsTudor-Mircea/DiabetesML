package utils;

import data.Instance;

public class Distance {
    DistanceType type;

    public Distance(DistanceType type) {
        this.type = type;
    }

    public double calculateDistance(Instance<Number, Integer> instance1, Instance<Number, Integer> instance2) {
        double sum = 0.0;
        int p = 0;

        if (type == DistanceType.manhattan)
            p = 1;

        if (type == DistanceType.euclidean)
            p = 2;

        for (int i = 0; i < instance1.getInput().size(); i++) {
            sum += Math.pow(Math.abs(
                    Double.parseDouble(instance1.getInput().get(i).toString()) -
                            Double.parseDouble(instance2.getInput().get(i).toString())
            ), p);
        }

        return Math.pow(sum, 1.0 / p);
    }

    public enum DistanceType {
        euclidean,
        manhattan
    }
}
