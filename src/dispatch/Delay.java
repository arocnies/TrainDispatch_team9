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

    public Delay(Node node, Edge edge, Train causeTrain, int cost) {
        this.node = node;
        this.edge = edge;
        this.causeTrain = causeTrain;
        this.cost = cost;
    }

    public Train getCause() {
        return causeTrain;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public List<Node> getNodes() {
        return Collections.singletonList(node);
    }

    @Override
    public List<Edge> getEdges() {
        return Collections.singletonList(edge);
    }
}
