package dispatch;

import graph.Edge;
import graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 4/12/2015.
 */

public abstract class AbstractDispatch {
    protected final Graph graph;
    protected final Map<Edge, List<Boolean>> lock; // A map of edges to the next integer time the edge is free.

    public AbstractDispatch(Graph graph, int duration) {
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
    protected abstract void  routeTrain(Train train);

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
}
