package graph;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Path {

    private final List<Node> nodes = new LinkedList<Node>();
    private int cost;

    void addNode(Node node) {
        nodes.add(node);
    }

    public int getCost() {

        return 0;
    }

    public List<Node> getNodes() {


        return null;
    }

    public List<Edge> getEdges() {


        return null;
    }

    public Path getSubpath() {


        return null;
    }
}
