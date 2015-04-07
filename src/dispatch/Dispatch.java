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
        // Loop through each edge.
            // If locked at time.
                // Split path. Add to Itinerary.
                // Delay until after. Add to Itinerary.
                // Start building new path.
            // Increment lock on edge.


    }

    // Checks if a path found is valid.
    public boolean isValidPath(Path path) {


        return false;
    }
}
