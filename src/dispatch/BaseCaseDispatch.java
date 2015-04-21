package dispatch;

import graph.Graph;
import graph.Path;

/**
 * Created by Aaron on 4/12/2015.
 */

public class BaseCaseDispatch extends Dispatch {

    public BaseCaseDispatch(Graph graph) {
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
        lockPath(path, time, path.getCost(), train);

        train.setItinerary(itin);
    }

    protected boolean isPathLocked(Path path, int start, int duration) {
        for (String edgeId : path.getEdgeNameSet()) {
            SlotLock sl = locks.get(edgeId);
            if (sl.isLocked(start, duration)) {
                return true;
            }
        }
        return false;
    }

    protected void lockPath(Path path, int start, int duration, Train t) {
        try {
            for (String edgeId : path.getEdgeNameSet()) {
                SlotLock<Train> sl = locks.get(edgeId);
                sl.acquireLock(start, duration, t);
            }
        }
        catch (InaccessibleLockException e) {
            e.printStackTrace();
        }
    }
}
