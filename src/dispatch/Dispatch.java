package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 4/12/2015.
 */

public class Dispatch {
    protected final Graph graph;
    protected final Map<String, SlotLock<Train>> locks;
    protected int time = 0;

    public Dispatch(Graph graph) {
        this.graph = graph;
        locks = new HashMap<>();

        // Fill locks map with slot locks.
        graph.getEdges().forEach(e -> locks.put(e.getSharedId(), new SlotLock<>()));
    }

    // Main method called to route the trains and return a plan.
    public Plan dispatchTrains(Schedule schedule) {

        // Routable all trains.
//        schedule.getTrains().forEach(this::routeTrain);
        List<Train> trains = new LinkedList<>(schedule.getTrains());
        for (Train t : trains) {
            routeTrain(t);
        }
        return new Plan(schedule);
    }

    // Routes a single train.
    protected void routeTrain(Train train) {
        train.setItinerary(getItinerary(train, null));
    }

    // Returns Itinerary from one Node to another.
    protected Itinerary getItinerary(Train train, Edge ignoredEdge) {

        // Start building Itinerary.
        // Start tracking simulated time.
        // Start building path.
        // Loop through each edge.
        // If locked at time.
        // Split path. Add to Itinerary.
        // Delay until after. Add to Itinerary.
        // Start building new path.
        // Increment lock on edge.

        // Setup.
        Node start = train.getStart();
        Node end = train.getEnd();
        time = train.getDepartureTime();
        Itinerary itin = new Itinerary(); // Start building Itinerary.
        Path path = graph.getPath(start, end, ignoredEdge); // Start building path.

        int stepCount = 1;
        for (Edge edge : path.getEdges()) {
            if (edge.getStart() != null) {

                SlotLock<Train> sl = locks.get(edge.getSharedId()); // Get lock.
                if (sl.isLocked(time, edge.getWeight())) { // If locked at time.

                    Path subPath = path.subPath(0, stepCount); // Split path.
//                    addTime(itin.addPath(subPath)); // Add path to itinerary.
                    itin.addPath(subPath); // Add path to itinerary.

                    int nextOpenTime = sl.nextOpen(time, edge.getWeight());
                    int wait = nextOpenTime - time;
                    Delay delay = new Delay(edge, train, wait, time); // Delay until after.
                    addTime(subPath.getCost() + itin.addDelay(delay)); // Add delay to itinerary.
                    path = graph.getPath(delay.getNode(), end, ignoredEdge); // Continue with new path.
                }

                // Try to acquire the lock for that edge.
                try {
                    sl.acquireLock(time, edge.getWeight(), train);
                    addTime(edge.getWeight());
                }
                catch (InaccessibleLockException e) {
                    e.printStackTrace();
                }
                stepCount++;
            }
        }

        itin.addPath(path);
        return itin;
    }

    protected void addTime(int t) {
        time += t;
    }
}
