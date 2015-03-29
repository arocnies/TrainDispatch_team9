package graph;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Edge implements Comparable<Edge> {
    private final Node start;
    private final Node end;
    private final int weight;

    public Edge(Node start, Node end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return getStart() + "-" + getWeight() + "-" + getEnd();
    }

    @Override
    public int compareTo(Edge edge) {
        return (this.getWeight() - edge.getWeight());
    }
}
