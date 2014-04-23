import java.util.List;
import java.util.ArrayList;

public class DecisionTree {
    private List<Data> examples;
    private Node tree;

    public DecisionTree(List<Data> examples) {
        this.examples = examples;
    }
    
    public void learn() {
        List<Attribute> attributes = new ArrayList<Attribute>();
        for (Attribute attribute : Attribute.values()) {
            attributes.add(attribute);
        }
        tree = decisionTreeLearning(examples, attributes, null);
    }
    
    public Rating predict(Data data) {
        Node current = tree;
        while (!current.isClassification()) {
            Attributable value = data.attributes[current.attribute.ordinal()];
            current = current.getChild(value);
        }
        return current.classification;
    }
    
    private Node decisionTreeLearning(
        List<Data> examples,
        List<Attribute> attributes,
        List<Data> parentExamples) {
        
        // Base case 1
        if (examples.isEmpty()) {
            return pluralityValue(parentExamples);
        }
        
        // Base case 2
        if (attributes.isEmpty()) {
            return pluralityValue(examples);
        }
        
        // Base case 3
        // If all examples have the same classification,
        // return that classification
        Rating classification = examples.get(0).rating;
        boolean same = true;
        for (Data example : examples) {
            if (example.rating != classification) {
                same = false;
                break;
            }
        }
        if (same) {
            return new Node(classification);
        }
        
        // Find attribute with the highest importance
        Attribute best = attributes.get(0);
        double max = 0;
        for (Attribute attribute : attributes) {
            double i = importance(attribute, examples);
            if (i > max) {
                max = i;
                best = attribute;
            }
        }
        
        attributes.remove(best);
        Node root = new Node(best);
        
        for (Attributable value : best.values) {
            List<Data> splitExamples = new ArrayList<Data>();
            for (Data example : examples) {
                if (example.attributes[best.ordinal()] == value) {
                    splitExamples.add(example);
                }
            }
            Node child = decisionTreeLearning(
                splitExamples, attributes, examples);
            root.addChild(child, value);
        }
        
        return root;
    }
    
    private Rating[] ratings = Rating.values();
    private int[] ratingPluralities = new int[ratings.length];
    
    // Return the most common classification
    private Node pluralityValue(List<Data> examples) {
    
        // Initialize array to all zeros
        for (int i = 0; i < ratingPluralities.length; i++) {
            ratingPluralities[i] = 0;
        }
        
        // Count the number of occurences of each rating
        for (Data example : examples) {
            ratingPluralities[example.rating.ordinal()] += 1;
        }
        
        // Find the maximum
        int max = 0;
        int maxi = 0;
        for (int i = 0; i < ratingPluralities.length; i++) {
            if (ratingPluralities[i] > max) {
                max = ratingPluralities[i];
                maxi = i;
            }
        }
        
        // Return a new rating node
        return new Node(ratings[maxi]);
    }
    
    // Calculate information gain for an attribute
    private double importance(Attribute attribute, List<Data> examples) {
        return 0;
    }
}