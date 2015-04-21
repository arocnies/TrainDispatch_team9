package dispatch;

import graph.Edge;
import graph.Node;

import java.util.Collections;
import java.util.List;

/**
 * Created by aaron on 4/4/15.
 */

public class Delay implements Routable {
    private final Node node;
    private final Edge edge;
    private final Train affectedTrain;
    private final int cost;
    private final int time;

    public Delay(Edge edge, Train affectedTrain, int cost, int time) {
        if (edge.getStart() != null) {
            this.node = edge.getStart();
        }
        else {
            this.node = edge.getEnd();
        }
        this.edge = edge;
        this.affectedTrain = affectedTrain;
        this.cost = cost;
        this.time = time;
    }

    public Train getAffectedTrain() {
        return affectedTrain;
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

    public int getTime() {
        return time;
    }
}
