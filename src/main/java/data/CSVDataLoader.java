package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVDataLoader {
    public static List<Instance<Number, Integer>> loadCSV(String filePath) {
        List<Instance<Number, Integer>> instances = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the header row
            if (br.readLine() != null) {
                System.out.println("Loaded dataset.");
            }

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                List<Number> features = new ArrayList<>();

                for (int i = 0; i < values.length - 1; i++) {
                    features.add(Double.parseDouble(values[i])); // Parse features
                }

                int label = Integer.parseInt(values[values.length - 1]); // Parse label (Outcome)

                instances.add(new Instance<>(features, label));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return instances;
    }
}
