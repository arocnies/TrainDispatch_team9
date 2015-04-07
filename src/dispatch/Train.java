package dispatch;

import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Train {
    private final Node startNode;
    private final Node endNode;
    private final int departureTime;
    private Itinerary itinerary;

    public Train(Node startNode, Node endNode, int departureTime) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.departureTime = departureTime;
    }

    public void setItinerary(Itinerary itin) {
        itinerary = itin;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public Node getStart() {
        return startNode;
    }

    public Node getEnd() {
        return endNode;
    }

    public int getDelay() {


        return 0;
    }
}
