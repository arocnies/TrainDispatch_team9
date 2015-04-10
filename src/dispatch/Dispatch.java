package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Dispatch {

    private Graph graph;
    private Map<Edge, Integer> lock; // A map of edges to the next integer time the edge is free.

    public Dispatch(Graph graph) {
        this.graph = graph;
        lock = new HashMap<>();

        // Fill lock map with zeros.
        for (Edge edge : graph.getEdges()) {
            lock.put(edge, 0);
        }
    }

    // Main method called to route the trains and return a plan.
    public Plan dispatchTrains(Schedule schedule) {

        // Route all trains.
        for (Train train : schedule.getTrains()) {
            routeTrain(train);
        }

//        return new Plan(schedule);
        return null;
    }

    // Routes a single train.
    protected void routeTrain(Train train) {
        Path directPath = graph.getPath(train.getStart(), train.getEnd());

        // Start building Itinerary.
        // Start tracking simulated time.

        // Start building path.
        // Loop through each edge.
            // If locked at time.
                // Split path. Add to Itinerary.
                // Delay until after. Add to Itinerary.
                // Start building new path.
            // Increment lock on edge.

        Itinerary itin = new Itinerary();
        int time = 0;
        Path path = graph.getPath(train.getStart(), train.getEnd());

        int stepCount = 1;
        for (Edge edge : path.getEdges()) {
            if (isLocked(edge, time)) { // If locked at time.
                Path subPath = path.subPath(0, stepCount); // Split path.
                time += itin.addPath(subPath); // Add path to itinerary.
                Delay delay = new Delay(subPath.getLastEdge(), train, getWait(edge)); // Delay until after.
                time += itin.addDelay(delay); // Add delay to itinerary.
                path = graph.getPath(delay.getNode(), train.getEnd()); // Begin with new path.
                lockEdge(edge, time); // Lock edge for using it.
            }
        }
    }

    private int getWait(Edge edge) {
        return 0;
    }

    private void lockEdge(Edge edge, int time) {
    }

    private boolean isLocked(Edge edge, int time) {
        return true;
    }

    // Checks if a path found is valid.
    public boolean isValidPath(Path path) {


        return false;
    }
}
