package utils;

import model.KNN;
import model.Model;
import model.NaiveBayes;
import model.Perceptron;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ModelFetcher {
    Properties properties;

    public ModelFetcher(String propertiesFile) throws IOException {
        properties = new Properties();

        properties.load(new FileReader(propertiesFile));
    }

    public Model<Number, Integer> getModel(String classifierType, String hyperParams) {
        Model<Number, Integer> model = null;

        switch (classifierType) {
            case "KNN": {
                String[] params = hyperParams.split(",");
                int k = Integer.parseInt(params[0]);
                String distanceType = params[1];
                System.out.println();

                if (distanceType.equals("manhattan"))
                    model = new KNN(k, Distance.DistanceType.manhattan);

                if (distanceType.equals("euclidean"))
                    model = new KNN(k, Distance.DistanceType.euclidean);

                break;
            }

            case "Perceptron": {
                String[] params = hyperParams.split(",");
                double learningRate = Double.parseDouble(params[0]);
                int epochs = Integer.parseInt(params[1]);
                System.out.println();

                model = new Perceptron(learningRate, epochs);

                break;
            }

            case "NaiveBayes": {
                model = new NaiveBayes();

                break;
            }
//
//            case "LogisticRegression": {
//                String[] params = hyperParams.split(",");
//                double learningRate = Double.parseDouble(params[0]);
//                int iterations = Integer.parseInt(params[1]);
//
//                model = new LogisticRegression(learningRate, iterations);
//
//                break;
//            }
//
//            case "DecisionTree": {
//                model = new DecisionTree();
//
//                break;
//            }

            default: {
                System.out.println("Invalid Model Type.");
                break;
            }
        }

        return model;
    }
}
