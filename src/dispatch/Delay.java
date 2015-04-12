package dispatch;

import graph.Edge;
import graph.Node;

import java.util.Collections;
import java.util.List;

/**
 * Created by aaron on 4/4/15.
 */

public class Delay implements Route {
    private final Node node;
    private final Edge edge;
    private final Train causeTrain;
    private final int cost;

    public Delay(Edge edge, Train causeTrain, int cost) {
        if (edge.getStart() != null) {
            this.node = edge.getStart();
        }
        else {
            this.node = edge.getEnd();
        }
        this.edge = edge;
        this.causeTrain = causeTrain;
        this.cost = cost;
    }

    public Train getCause() {
        return causeTrain;
    }

    public int getCost() {
        return cost;
    }

    public Node getNode() {
        return node;
    }

    public Edge getEdge() {
        return edge;
    }

    @Override
    public List<Edge> getEdges() {
        return Collections.singletonList(edge);
    }

    @Override
    public List<Node> getNodes() {
        return Collections.singletonList(node);
    }
}
