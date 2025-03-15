package gui;

import data.CSVDataLoader;
import data.DataSplitter;
import data.Instance;
import evaluation.Accuracy;
import evaluation.F1Score;
import evaluation.Precision;
import evaluation.Recall;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Model;
import utils.ModelFetcher;

import java.util.ArrayList;
import java.util.List;

public class MainWindowController {
    Model<Number, Integer> model;
    List<Instance<Number, Integer>> testSet;


    @FXML
    private void initialize() {
        testButton.setDisable(true);

        trainLabel.textProperty().bind(sldSplit.valueProperty().asString("%.2f"));
        testLabel.textProperty().bind(
                Bindings.createStringBinding(
                        () -> String.format("%.2f", 100 - sldSplit.getValue()),
                        sldSplit.valueProperty()
                )
        );

        List<String> lst = new ArrayList<>();
        lst.add("KNN");
        lst.add("Perceptron");
        lst.add("NaiveBayes");
        lst.add("LogisticRegression");
        lst.add("DecisionTree");

        classifierList = FXCollections.observableArrayList(lst);

        cmbClassifier.setItems(classifierList);
    }

    @FXML
    private void train() {
        try {
            String inputFilePath = "src/main/resources/data/" + txtInputFilePath.getText();
            String classifierType = cmbClassifier.getSelectionModel().getSelectedItem();
            double trainTestPercentage = sldSplit.getValue() / 100.0;

            // load the dataset
            List<Instance<Number, Integer>> dataset = CSVDataLoader.loadCSV(inputFilePath);

            // split the data into training and test sets
            List<Instance<Number, Integer>> trainSet = new ArrayList<>();
            testSet = new ArrayList<>();
            DataSplitter.splitData(dataset, trainTestPercentage, trainSet, testSet);

            // fetch and train the model
            ModelFetcher fetcher = new ModelFetcher("src/main/resources/config/settings.properties");

            model = fetcher.getModel(classifierType, txtHyperParameters.getText());

            model.train(trainSet);

            testButton.setDisable(false);
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    private void test() {
        try {
            List<Integer> testResults = model.test(testSet);

            Accuracy accuracy = new Accuracy();
            String accuracyResults = String.valueOf(Math.round(accuracy.evaluate(testSet, testResults) * 100.0));
            accuracyLabel.setText(accuracyResults + "%");

            Precision precision = new Precision();
            String precisionResults = String.valueOf(Math.round(precision.evaluate(testSet, testResults) * 100.0));
            precisionLabel.setText(precisionResults + "%");

            Recall recall = new Recall();
            String recallResults = String.valueOf(Math.round(recall.evaluate(testSet, testResults) * 100.0));
            recallLabel.setText(recallResults + "%");

            F1Score f1Score = new F1Score();
            String f1ScoreResults = String.valueOf(Math.round(f1Score.evaluate(testSet, testResults) * 100.0));
            f1ScoreLabel.setText(f1ScoreResults + "%");
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    public TextField txtInputFilePath;

    @FXML
    public Label trainLabel, testLabel;

    @FXML
    public Slider sldSplit;

    @FXML
    public TextField txtHyperParameters;

    @FXML
    public Label accuracyLabel, precisionLabel, recallLabel, f1ScoreLabel;

    @FXML
    public Button testButton;

    @FXML
    ComboBox<String> cmbClassifier;

    @FXML
    ObservableList<String> classifierList;
}
