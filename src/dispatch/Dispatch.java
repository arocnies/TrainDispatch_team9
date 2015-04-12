package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Dispatch {

    private final Graph graph;
    private final Map<Edge, List<Boolean>> lock; // A map of edges to the next integer time the edge is free.

    public Dispatch(Graph graph, int duration) {
        this.graph = graph;
        lock = new HashMap<>(graph.getEdges().size());

        // Fill lock map with zeros.
        for (Edge edge : graph.getEdges()) {
            ArrayList<Boolean> edgeLock = new ArrayList<>(duration);
            for (int i = 0; i < duration; i++) {
                edgeLock.add(false);
            }
            lock.put(edge, edgeLock);
        }
    }

    // Main method called to route the trains and return a plan.
    public Plan dispatchTrains(Schedule schedule) {

        // Route all trains.
        schedule.getTrains().forEach(this::routeTrain);

        return new Plan(schedule);
    }

    // Routes a single train.
    protected void routeTrain(Train train) {

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

        try {
            Itinerary itin = new Itinerary(); // Start building Itinerary.
            int time = 0; // Start tracking simulated time.
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
                lockEdge(edge, time); // Lock edge for using it.
                stepCount++;
            }

            // Done routing, save to train.
            itin.addPath(path);
            train.setItinerary(itin);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Train cannot be routed in time");
        }
    }

    private int getWait(Edge edge, int time) {
        int wait = 0;

        for (int i = time; isLocked(edge, i); i++) {
            wait++;
        }
        return wait;
    }

    private void lockEdge(Edge edge, int time) {
        prepLock(edge, time);
        List<Boolean> edgeLock = lock.get(edge);

        for (int i = time; i < edge.getWeight(); i++) {
            edgeLock.set(i, true);
        }
    }

    private boolean isLocked(Edge edge, int time) {
        prepLock(edge, time);
        List<Boolean> edgeLock = lock.get(edge);

        for (int i = time; i < edge.getWeight(); i++) {
            if (edgeLock.get(i)) return true;
        }
        return false;
    }

    // Insures the lock will be big enough for operations.
    private void prepLock(Edge edge, int time) {
        List<Boolean> edgeLock = lock.get(edge);

        if (edge.getStart() != null && edgeLock.size() < time + edge.getWeight()) {
            for (int i = 0; i < edge.getWeight(); i++) {
                edgeLock.add(false);
            }
        }
    }
}
