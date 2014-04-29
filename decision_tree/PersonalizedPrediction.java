import java.io.File;
import java.io.FileReader;
import java.util.List;

public class PersonalizedPrediction {
    public static void main(String[] args) throws Exception {
    
        Parser parser = new Parser(new FileReader(new File("data_set/data")));
        List<Data> trainingExamples = parser.run(1000000, 0);
        List<Data> testingExamples = parser.run(1000000, 0);
        
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
        
        Node n = dt.getRoot();
        Evaluate g = new Evaluate(n, testingExamples);
        g.guessRating();
    }
}