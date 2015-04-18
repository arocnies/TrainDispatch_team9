package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Dispatch extends AbstractDispatch {

    public Dispatch(Graph graph, int duration) {
        super(graph, duration);
    }

    // Routes a single train.
    protected void routeTrain(Train train, Edge badEdge) {

        // Start building Itinerary.
        // Start tracking simulated time.

        // Start building path.
        // Loop through each edge.
            // If locked at time.
                // Split path. Add to Itinerary.
                // Delay until after. Add to Itinerary.
                // Start building new path.
            // Increment lock on edge.

        // ---------------------------------------

        Itinerary itin = new Itinerary(); // Start building Itinerary.
        int time = train.getDepartureTime(); // Start tracking simulated time.
        Path path = graph.getPath(train.getStart(), train.getEnd(), badEdge); // Start building path.

        int stepCount = 1;
        for (Edge edge : path.getEdges()) {
            if (isLocked(edge, time)) { // If locked at time.
                Path subPath = path.subPath(0, stepCount); // Split path.
                time += itin.addPath(subPath); // Add path to itinerary.
                Delay delay = new Delay(subPath.getLastEdge(), train, getWait(edge, time), time); // Delay until after.
                time += itin.addDelay(delay); // Add delay to itinerary.
                path = graph.getPath(delay.getNode(), train.getEnd(), badEdge); // Continue with new path.
            }
            lockEdge(edge, time, time + edge.getWeight()); // Lock edge for using it.
            stepCount++;
        }

        // Done routing, save to train.
        itin.addPath(path);
        train.setItinerary(itin);
    }

    @Override
    protected void routeTrain(Train train) {
        routeTrain(train, null);
    }
}
