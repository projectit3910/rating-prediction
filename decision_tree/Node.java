import java.util.Map;
import java.util.HashMap;

public class Node {

    public Attribute attribute;
    private Node[] children;
    
    public Node(Attribute attribute) {
        this.attribute = attribute;
        children = new Node[attribute.values.length];
    }
    
    public Rating classification = null;
    
    public Node(Rating classification) {
        this.classification = classification;
    }
    
    public boolean isClassification() {
        return classification != null;
    }
    
    public void addChild(Node node, Attributable value) {
        children[value.ordinal()] = node;
    }
    
    public Node getChild(Attributable value) {
        return children[value.ordinal()];
    }
}