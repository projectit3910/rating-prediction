import java.io.InputStreamReader;
import java.lang.System;
import java.util.List;

public class RatingPrediction {
    // Entry point
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new InputStreamReader(System.in));
        List<Data> examples = parser.run();
        
        for (Data example : examples) {
            System.out.println(
                example.genre + " " +
                example.gender + " " +
                example.age + " " +
                example.occupation + " " +
                example.rating);
        }
    }
}