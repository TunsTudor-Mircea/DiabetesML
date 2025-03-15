# DiabetesML - AI-Powered Diabetes Prediction

**DiabetesML** is a Java-based machine learning classifier that predicts the likelihood of diabetes based on medical data. It provides an intuitive GUI built with JavaFX and allows users to apply multiple machine learning models for diabetes risk prediction.

## Features

- **Machine Learning Models**: K-Nearest Neighbors (KNN), Naive Bayes, Perceptron, Decision Tree, and Logistic Regression.
- **GUI Interface**: Built with JavaFX for an easy-to-use interface for inputting data and displaying results.
- **Customizable Hyperparameters**: Each model comes with adjustable hyperparameters for tuning.
- **Data Evaluation**: Evaluates models using **Accuracy**, **Precision**, **Recall**, and **F1 Score**.
- **Data Splitting**: Customizable training/testing split for data.
- **CSV Data Support**: Easily load your data from CSV files for predictions.

## Technologies Used

- **Java** - Primary language for the project.
- **Maven** - Project management tool for dependencies and build automation.
- **JavaFX** - Used for the GUI interface.


## Usage

1. ### **Load Data:** Use the GUI to load a CSV file containing patient data. The data should be in the following format (example):
```console
Pregnancies,Glucose,BloodPressure,SkinThickness,Insulin,BMI,DiabetesPedigreeFunction,Age,Outcome
6,148,72,35,0,33.6,0.627,50,1
1,85,66,29,0,26.6,0.351,31,0
```

2. ### **Choose Model:** Select a machine learning model from the following:
   
- K-Nearest Neighbors (KNN): A simple, instance-based learning algorithm.

- Naive Bayes: A probabilistic classifier based on Bayes’ theorem.

- Perceptron: A basic neural network-based algorithm for binary classification.

- Decision Tree: A tree-based model for classification that splits data based on features.

- Logistic Regression: A statistical method for binary classification, predicting the probability of an outcome.

3. ### **Adjust Hyperparameters:** Customize the hyperparameters for each model via the GUI:

- KNN: Number of neighbors, distance metric

- Perceptron: Learning rate, number of iterations

- Logistic Regression: Learning rate, number of iterations

4. ### **Train and Test:** Adjust the training/testing split ratio (e.g., 80/20 or 70/30) using the GUI.

5. ### **Evaluate the Model:** After training, the system will display evaluation metrics such as:
   
- Accuracy: Proportion of correctly predicted instances.

- Precision: Proportion of positive predictions that are correct.

- Recall: Proportion of actual positives that are correctly identified.

- F1 Score: The harmonic mean of precision and recall.

## Future

🔹 Deploy as a web API for real-world usage

🔹 Integrate with a mobile app for easy accessibility
