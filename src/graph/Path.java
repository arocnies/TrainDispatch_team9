package graph;

import dispatch.Route;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Path implements Route, Comparable<Path>, Iterable<Edge> {

    private final List<Edge> edges = new ArrayList<>();
    private final Set<Node> nodes = new HashSet<>();
    private int cost;

    public Path(Node startNode) {
        addEdge(new Edge(null, startNode, 0));
    }

    public Path(Path path) {
        edges.addAll(path.getEdges());
    }

    void addEdge(Edge edge) {
        edges.add(edge);
        nodes.add(edge.getStart());
        nodes.add(edge.getEnd());
        cost += edge.getWeight();
    }

    public Edge getLastEdge() {
        return edges.get(edges.size() - 1);
    }

    public Node getLastNode() {
        return getLastEdge().getEnd();
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public List<Node> getNodes() {
        return new ArrayList<>(nodes);
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public int compareTo(Path path) {
        return (this.getCost() - path.getCost());
    }

    @Override
    public Iterator<Edge> iterator() {
        return edges.iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge edge : edges) {
            sb.append(edge + " ");
        }
        return sb.toString();
    }

    public Path subPath(int start, int stepCount) {
        List<Edge> myEdges = this.getEdges();
        Path path = new Path(myEdges.get(start).getEnd());
        for (int i = start + 1; i < start + stepCount && i < myEdges.size(); i++) {
            path.addEdge(myEdges.get(i));
        }

        return path;
    }
}
