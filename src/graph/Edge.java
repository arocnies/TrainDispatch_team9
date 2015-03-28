package graph;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Edge {
    private final Node nodeA;
    private final Node nodeB;
    private int weight;

    public Edge(Node nodeA, Node nodeB, int weight) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.weight = weight;
    }

    public Node getNodeB() {
        return nodeB;
    }

    public int getWeight() {
        return weight;
    }
}
