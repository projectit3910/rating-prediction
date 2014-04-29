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
        
        // All (almost) examples shuffled randomly
        List<Data> examples = new Parser(new InputStreamReader(System.in))
            .run(2000000, 0);
        Collections.shuffle(examples, new Random(0));
        
        plot1(examples);
        plot2(examples);
    }
    
    private static void plot1(List<Data> examples) {
        
        // X-axis is training set size
        int interval = 50000;
        double[] x = new double[1000000 / interval];
        for (int i = 0; i < x.length; i++) {
            x[i] = interval * (i+1);
        }
        
        // Y-axis is correct predictions over testing set size (1,000,000)
        double[] y = new double[x.length];
        
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
                int prediction = dt.predict(data).ordinal();
                int actual = data.rating.ordinal();
                if (prediction == actual) {
                    correct++;
                }
            }
            y[i] = (double)correct / (double)testingSet.size();
        }
        
        // Plot the data
        Plot2DPanel plot = new Plot2DPanel();
        String title = "Prediction accuracy vs. size of training set using decision tree learning";
        plot.addLinePlot(title, x, y);
        plot.setAxisLabels("Size of training set", "Prediction accuracy");
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
    
    private static void plot2(List<Data> examples) {
    
        // Bar plot
        DecisionTree dt = new DecisionTree(examples);
        dt.learn();
        
        double[] ratingSums = new double[Occupation.values().length];
        for (int i = 0; i < ratingSums.length; i++) {
            ratingSums[i] = 0;
        }
        for (Gender gender : Gender.values()) {
            for (Age age : Age.values()) {
                for (Occupation occupation : Occupation.values()) {
                    int prediction = dt.predict(new Data(Genre.HORROR, gender, age, occupation, null, 0)).ordinal();
                    ratingSums[occupation.ordinal()] += prediction;
                }
            }
        }
        for (int i = 0; i < ratingSums.length; i++) {
            System.out.print(Occupation.values()[i]);
            System.out.print(" ");
            System.out.println(ratingSums[i]);
        }
    }
}