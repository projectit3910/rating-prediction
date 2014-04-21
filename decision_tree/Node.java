import java.util.Map;
import java.util.HashMap;

public class Node {

    private AttributeType type;
    private Map<Attribute, Node> children;
    
    // Only used for rating nodes (classifications)
    private Rating classification;

    public Node(AttributeType type) {
        this.type = type;
        children = new HashMap<Attribute, Node>();
    }
    
    public Node(Rating rating) {
        type = AttributeType.RATING;
        classification = rating;
    }
    
    public void addChild(Node node, Attribute value) {
        children.put(value, node);
    }
}