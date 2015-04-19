package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 4/12/2015.
 */

public class Dispatch {
    protected final Graph graph;
    protected final Map<Edge, List<Boolean>> lock; // A map of edges to the next integer time the edge is free.
    //TODO: Refactor with objected based locking.

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

        // Routable all trains.
        schedule.getTrains().forEach(this::routeTrain);
        return new Plan(schedule);
    }

    // Routes a single train.
    protected void routeTrain(Train train) {
        train.setItinerary(getItinerary(train));
    }

    // Returns Itinerary from one Node to another.
    protected Itinerary getItinerary(Train train) {

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
        int time = train.getDepartureTime();
        Itinerary itin = new Itinerary(); // Start building Itinerary.
        Path path = graph.getPath(start, end); // Start building path.

        int stepCount = 1;
        for (Edge edge : path.getEdges()) {
            if (isLocked(edge, time)) { // If locked at time.
                Path subPath = path.subPath(0, stepCount); // Split path.
                time += itin.addPath(subPath); // Add path to itinerary.
                Delay delay = new Delay(subPath.getLastEdge(), train, getWait(edge, time), time); // Delay until after.
                time += itin.addDelay(delay); // Add delay to itinerary.
                path = graph.getPath(delay.getNode(), end); // Continue with new path.
            }
            lockEdge(edge, time, time + edge.getWeight()); // Lock edge for using it.
            stepCount++;
        }

        itin.addPath(path);
        return itin;
    }

    protected int getWait(Edge edge, int time) {
        int wait = 0;
        for (int i = time; isLocked(edge, i); i++) {
            wait++;
        }
        return wait;
    }

    protected void lockEdge(Edge edge, int start, int finish) {
        if (edge.getStart() != null) {
            prepLock(edge, finish);
            List<Boolean> edgeLock = lock.get(edge);

            for (int i = start; i < finish; i++) {
                edgeLock.set(i, true);
            }
        }
    }

    protected boolean isLocked(Edge edge, int time) {
        prepLock(edge, time);
        return isLocked(edge, time, time + edge.getWeight());
    }

    protected boolean isLocked(Edge edge, int start, int end) {
        if (edge.getStart() != null) {
            prepLock(edge, end);
            List<Boolean> edgeLock = lock.get(edge);

            for (int i = start; i < end; i++) {
                if (edgeLock.get(i)) return true;
            }
        }
        return false;
    }

    // Insures the lock will be big enough for operations.
    protected void prepLock(Edge edge, int time) {
        List<Boolean> edgeLock = lock.get(edge);

        while (edge.getStart() != null && edgeLock.size() < time + edge.getWeight()) {
            edgeLock.add(false);
        }
    }

    protected void unlock(Train train) {
        Itinerary itin = train.getItinerary();
        int time = train.getDepartureTime();

        for (Routable r : itin.getElements()) {
            if (r instanceof Path) { // Ugh... This should be redesigned with object structured locking.
                for (Edge edge : r.getEdges()) {
                    List<Boolean> edgeLock = lock.get(edge);
                    for (int i = time; i < time + edge.getWeight(); i++) {
                        prepLock(edge, i);
                        edgeLock.set(i, false);
                    }
                    time += edge.getWeight();
                }
            }
            else if (r instanceof Delay){ // Else it is a delay.
                time += r.getCost();
            }
        }
    }
}
