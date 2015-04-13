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

        // Start building Itinerary.
        // Start tracking simulated time.

        // Start building path.
        // If time through path cost is locked.
            // increment time to next free time.
        // If time was incremented, add delay.
        // Lock all edges.

        // ---------------------------------------

        Itinerary itin = new Itinerary(); // Start building Itinerary.
        int time = train.getDepartureTime(); // Start tracking simulated time.
        final Path path = graph.getPath(train.getStart(), train.getEnd()); // Start building path.

        while (isLocked(path, time, time + path.getCost())) {
            time++;
        }
        if (time != train.getDepartureTime()) {
            Delay delay = new Delay(path.getEdges().get(1), train, time - train.getDepartureTime());
            itin.addDelay(delay);
        }
        itin.addPath(path);
        final int finalTime = time;
        itin.getEdges().forEach(e -> lockEdge(e, finalTime, finalTime + path.getCost()));

        train.setItinerary(itin);
    }

    protected boolean isLocked(Path path, int start, int end) {
        for (Edge edge : path.getEdges()) {
            if (isLocked(edge, start, end)) {
                return true;
            }
        }
        return false;
    }
}
