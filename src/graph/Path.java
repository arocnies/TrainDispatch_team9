package graph;

import dispatch.Route;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Path implements Route, Comparable<Path>, Iterable<Edge> {

    private final List<Edge> edges = new ArrayList<>();
    private final Set<Node> nodes = new HashSet<>();
    private final List<Node> nodeList = new LinkedList<>();
    private int cost;

    public Path(Node startNode) {
        addEdge(new Edge(null, startNode, 0));
    }

    public Path(Path path) {
        edges.addAll(path.getEdges());
    }

    void addEdge(Edge edge) {
        edges.add(edge);
        addNode(edge.getStart());
        addNode(edge.getEnd());
        cost += edge.getWeight();
    }

    private void addNode(Node n) {
        nodes.add(n);
        nodeList.add(n);
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
        return nodeList;
    }

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
}
