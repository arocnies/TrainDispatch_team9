package graph;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Edge {
    private final Node nodeAlpha;
    private final Node nodeBeta;
    private int weight;

    public Edge(Node nodeAlpha, Node nodeBeta, int weight) {
        this.nodeAlpha = nodeAlpha;
        this.nodeBeta = nodeBeta;
        this.weight = weight;
    }

    public Node getNodeAlpha() {
        return nodeAlpha;
    }

    public Node getNodeBeta() {
        return nodeBeta;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getNodeAlpha() + "-" + getWeight() + "-" + getNodeBeta();
    }
}
