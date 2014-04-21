import java.util.List;
import java.util.ArrayList;

public class DecisionTreeLearner {
    private List<Data> examples;

    public DecisionTreeLearner(List<Data> examples) {
        this.examples = examples;
    }
    
    public Node learn() {
        List<AttributeType> attributes = new ArrayList<AttributeType>();
        for (AttributeType type : AttributeType.values()) {
            attributes.add(type);
        }
        attributes.remove(AttributeType.RATING);
        return decisionTreeLearning(examples, attributes, null);
    }
    
    private Node decisionTreeLearning(
        List<Data> examples,
        List<AttributeType> attributes,
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
        AttributeType bestAttribute;
        double max = 0;
        for (AttributeType attribute : attributes) {
            double i = importance(attribute, examples);
            if (i > max) {
                max = i;
                bestAttribute = attribute;
            }
        }
        
        return null;
    }
    
    // Return the most common classification
    private Node pluralityValue(List<Data> examples) {
        return null;
    }
    
    // Calculate information gain for an attribute
    private double importance(AttributeType attribute, List<Data> examples) {
        return 0;
    }
}