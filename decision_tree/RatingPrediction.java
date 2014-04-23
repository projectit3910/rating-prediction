import java.io.InputStreamReader;
import java.lang.System;
import java.util.List;

public class RatingPrediction {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new InputStreamReader(System.in));
        List<Data> trainingExamples = parser.run(2000000);
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
        dt.printTree();
    }
}