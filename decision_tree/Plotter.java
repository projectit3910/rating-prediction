import java.io.InputStreamReader;
import java.lang.System;
import java.util.*;
import org.math.plot.*;
import javax.swing.JFrame;
import org.math.plot.plotObjects.*;
import java.awt.Color;
import java.awt.Font;

public class Plotter {
    public static void main(String[] args) throws Exception {
    
        Parser parser = new Parser(new InputStreamReader(System.in));
        
        // X-axis is training set size
        int interval = 50000;
        double[] x = new double[1000000 / interval];
        for (int i = 0; i < x.length; i++) {
            x[i] = interval * (i+1);
        }
        
        // Y-axis is correct predictions over testing set size (1,000,000)
        double[] y = new double[x.length];
        
        // All (almost) examples shuffled randomly
        List<Data> examples = parser.run(2000000, 0);
        Collections.shuffle(examples, new Random(0));
        
        // Testing set used to calculate each accuracy ratio
        List<Data> testingSet = new ArrayList<Data>();
        for (int i = 0; i < 1000000; i++) {
            testingSet.add(examples.get(1000000 + i));
        }
        
        // Calculate Y values
        for (int i = 0; i < x.length; i++) {
            List<Data> trainingSet = new ArrayList<Data>();
            for (int j = 0; j < x[i]; j++) {
                trainingSet.add(examples.get(j));
            }
            DecisionTree dt = new DecisionTree(trainingSet);
            dt.learn();
            int correct = 0;
            for (Data data : testingSet) {
                if (dt.predict(data) == data.rating) {
                    correct++;
                }
            }
            y[i] = (double)correct / (double)testingSet.size();
        }
        
        // Plot the data
        Plot2DPanel plot = new Plot2DPanel();
        String title = "Prediction accuracy vs. size of training set using decision tree learning";
        plot.addLinePlot(title, x, y);
        plot.setAxisLabels("Size of training set",
            "Prediction accuracy (Correct predictions / total predictions)");
        plot.getAxis(0).setLabelPosition(0.5, -0.15);
        plot.getAxis(0).setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getAxis(1).setLabelPosition(-0.15, 0.5);
        plot.getAxis(1).setLabelFont(new Font("Arial", Font.PLAIN, 14));
        plot.getAxis(1).setLabelAngle(-3.14 / 2);
        BaseLabel titleLabel = new BaseLabel(title, Color.BLACK, 0.5, 1.1);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        plot.addPlotable(titleLabel);
        
        JFrame frame = new JFrame(title);
        frame.setSize(800, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}