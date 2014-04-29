import java.io.File;
import java.io.FileReader;
import java.util.List;

public class PrintTree {
    public static void main(String[] args) throws Exception {
        Parser parser = new Parser(new FileReader(new File("data_set/data")));
        List<Data> trainingExamples = parser.run(2000000, 0);
        DecisionTree dt = new DecisionTree(trainingExamples);
        dt.learn();
        dt.printTree();
    }
}