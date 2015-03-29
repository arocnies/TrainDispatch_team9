package graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Path implements Comparable<Path>, Iterable<Edge> {

    private final List<Edge> edges = new ArrayList<>();
    private int cost;

    public Path(Node startNode) {
        addEdge(new Edge(null, startNode, 0));
    }

    public Path(Path path) {
        edges.addAll(path.getEdges());
    }

    void addEdge(Edge edge) {
        edges.add(edge);
        cost += edge.getWeight();
    }

    public Edge getLastEdge() {
        return edges.get(edges.size() - 1);
    }

    public Node getLastNode() {
        return getLastEdge().getEnd();
    }

    public int getCost() {

        return cost;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : edges) {
            sb.append(edge + " ");
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Path path) {
        return (this.getCost() - path.getCost());
    }

    @Override
    public Iterator<Edge> iterator() {
        return edges.iterator();
    }
}
