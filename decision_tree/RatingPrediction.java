import java.io.InputStreamReader;
import java.lang.System;
import java.util.List;

public class RatingPrediction {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new InputStreamReader(System.in));
        List<Data> trainingExamples = parser.run(1500000);
        List<Data> testingExamples = parser.run(500000);
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
        
        int total = 500000;
        int correct = 0;
        int close = 0;
        for (int i = 0; i < total; i++) {
            Data example = testingExamples.get(i);
            Rating prediction = dt.predict(example);
            if (example.rating == prediction) {
                correct++;
            }
            if (example.rating.ordinal() == prediction.ordinal() + 1 ||
                example.rating.ordinal() == prediction.ordinal() - 1) {
                close++;
            }
        }
        
        System.out.print("Correct (+-0): ");
        System.out.println((double)correct / (double)total);
        System.out.print("Close (+-1): ");
        System.out.println(((double)correct + (double)close) / (double)total);
    }
}