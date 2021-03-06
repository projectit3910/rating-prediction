import java.io.File;
import java.io.FileReader;
import java.util.List;

public class RatingPrediction {
    public static void main(String[] args) throws Exception {
    
        Parser parser = new Parser(new FileReader(new File("data_set/data")));
        List<Data> trainingExamples = parser.run(1000000, 0);
        List<Data> testingExamples = parser.run(1000000, 0);
        
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
        
        for (Data example : testingExamples) {
            int prediction = dt.predict(example).ordinal() + 1;
            int actual = example.rating.ordinal() + 1;
            
            System.out.println("Prediction: " + Integer.toString(prediction) +
                "; Actual: " + Integer.toString(actual));
        }
    }
}