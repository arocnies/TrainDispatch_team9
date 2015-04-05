package dispatch;

import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Train {
    private final Node startNode;
    private final Node endNode;
    private final int departureTime;
    private Route route;

    public Train(Node startNode, Node endNode, int departureTime) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.departureTime = departureTime;
    }

    public Route getRoute() {
        return route;
    }
}
