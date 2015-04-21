package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;

/**
 * Created by Aaron on 4/12/2015.
 */

public class BaseCaseDispatch extends Dispatch {

    public BaseCaseDispatch(Graph graph, int duration) {
        super(graph);
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

        while (isPathLocked(path, time, path.getCost())) {
            time++;
        }
        if (time != train.getDepartureTime()) {
            Delay delay = new Delay(path.getEdges().get(1), train, time - train.getDepartureTime(), time);
            itin.addDelay(delay);
        }
        itin.addPath(path);

        // Lock edges.
        for (Edge edge : itin.getEdges()) {
            if (edge.getStart() != null) {
                try {
                    SlotLock<Train> sl = locks.get(edge.getSharedId());
                    sl.acquireLock(time, time + path.getCost(), train);
                }
                catch (InaccessibleLockException e) {
                    e.printStackTrace();
                }
            }
        }

        train.setItinerary(itin);
    }

    protected boolean isPathLocked(Path path, int start, int duration) {
        for (Edge edge : path.getEdges()) {
            if (edge.getStart() != null) {
                SlotLock sl = locks.get(edge.getSharedId());
                if (sl.isLocked(start, duration)) {
                    return true;
                }
            }
        }
        return false;
    }
}
