package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;

/**
 * Created by Aaron on 4/12/2015.
 */

public class BaseCaseDispatch extends AbstractDispatch {

    public BaseCaseDispatch(Graph graph, int duration) {
        super(graph, duration);
    }

    @Override
    protected void routeTrain(Train train) {
        Itinerary itin = new Itinerary(); // Start building Itinerary.
        int time = train.getDepartureTime(); // Start tracking simulated time.
        Path path = graph.getPath(train.getStart(), train.getEnd()); // Start building path.


        int stepCount = 1;
        for (Edge edge : path.getEdges()) {
            if (isLocked(edge, time)) { // If locked at time.
                Path subPath = path.subPath(0, stepCount); // Split path.
                time += itin.addPath(subPath); // Add path to itinerary.
                Delay delay = new Delay(subPath.getLastEdge(), train, getWait(edge, time)); // Delay until after.
                time += itin.addDelay(delay); // Add delay to itinerary.
                path = graph.getPath(delay.getNode(), train.getEnd()); // Continue with new path.
            }

            // lock all edges in itinerary until for this edge.
            stepCount++;
        }
        time += itin.addPath(path);
        train.setItinerary(itin);

        final int finalTime = time;
        itin.getEdges().forEach(e -> lockEdge(e, train.getDepartureTime(), finalTime));
    }
}
