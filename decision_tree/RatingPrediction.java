import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.lang.System;
import java.util.List;

public class RatingPrediction {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new FileReader(new File("data_set/data")));
        List<Data> trainingExamples = parser.run(200000, 0);
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
       // dt.printTree();
        Node n = dt.getRoot();
        
        List<Data> testingExamples = parser.run(10000, 200000);
        
        Evaluate g = new Evaluate(n, testingExamples);
        g.guessRating();
        
        
    }
}