package dispatch;

import graph.*;

import java.util.*;

/**
 * Created by aaron on 4/16/15.
 */

public class OptimizedDispatch extends Dispatch {
    private WaitingEdgeComparator wec = new WaitingEdgeComparator(this);

    public OptimizedDispatch(Graph graph, int duration) {
        super(graph);
    }

    @Override
    public void addTime(int t) {
        super.addTime(t);
        wec.setTime(time);
    }

    @Override
    protected void routeTrain(Train train) {
        routeTrain(train, null);
    }

    protected void routeTrain(Train train, Edge edge) {
        train.setItinerary(getItinerary(train, edge));
    }

    @Override
    public Plan dispatchTrains (Schedule schedule) {
        graph.setEdgeComparator(new WaitingEdgeComparator(this));
        Plan plan = super.dispatchTrains(schedule);

        for (int i = 0; i < 5; i++) {
            // Make list of Delays.
            List<Delay> delays = new LinkedList<>();
            plan.getTrains().forEach(t -> delays.addAll(t.getItinerary().getDelays()));
            delays.sort(new DelayComparator());

            // Make list of delayed trains.
            List<Train> trains = new LinkedList<>();
            for (Delay d : delays) {
                if (!trains.contains(d.getAffectedTrain())) {
                    trains.add(d.getAffectedTrain());
                }
            }

            // Unlock all delayed trains.
            for (Train t : trains) {
                List<Edge> edges = t.getItinerary().getEdges();
                edges.forEach(e -> locks.get(e.getSharedId()).evictHolder(t));
            }

            // Reroute trains.
            trains.forEach(this::routeTrain);
        }


        // --------------------------------------
//        // Setup raw plan.
//        Plan plan = super.dispatchTrains(schedule);
//        double rawDelay = plan.getAverageDelay();
//
//        // Build delay queue.
//        List<Delay> delays = new LinkedList<>();
//
//        for (Train t : plan.getTrains()) {
//            t.getItinerary().getDelays().forEach(delays::add);
//        }
//
//        // Prioritize delays based on comparator.
//        delays.sort(new DelayComparator());
//        delays.forEach(d -> unlock(d.getAffectedTrain()));
////        for (int i = 0; i < 3; i++) {
////            unlock(delays.get(i).getAffectedTrain());
////        }
//
//        // Unlock routes for all Trains that have delays.
////        unlockAll();
////        plan.getTrains().forEach(this::unlock);
//
//        // Set edge comparator to account for routed trains.
//        graph.setEdgeComparator(new WaitingEdgeComparator(this));
//
//        delays.forEach(d -> routeTrain(d.getAffectedTrain()));
        graph.setEdgeComparator(new EdgeComparator());
        return plan;
    }

//    private Itinerary findOptimalRoute(Train train, Comparator<? extends Edge> comparator) {
//
//        // Setup.
//        Itinerary optimizedItin = new Itinerary();
//        Node startNode = train.getStart();
//        Node endNode = train.getEnd();
//        Path path = new Path(startNode);
//
//        // Minimum distance from startNode to key node.
//        Map<Node, Path> minPaths = new HashMap<>(graph.getNodes().size());
//        for (Node n : graph.getNodes()) {
//            minPaths.put(n, new Path(startNode));
//        }
//
//        // Priority queue by weight.
//        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();
//
//        return optimizedItin;
//    }

    class WaitingEdgeComparator implements Comparator<Edge> {

        private final Dispatch dispatch;
        private int time = 0;

        public WaitingEdgeComparator(Dispatch dispatch) {
            this.dispatch = dispatch;
        }

        void setTime(int t) {
            time = t;
        }

        @Override
        public int compare(Edge e1, Edge e2) {

            SlotLock s1 = locks.get(e1.getSharedId());
            SlotLock s2 = locks.get(e2.getSharedId());

            int w1 = Integer.MAX_VALUE;
            int w2 = Integer.MAX_VALUE;

            if (s1 != null) {
                w1 = e1.getWeight() + s1.nextOpen(time, e1.getWeight());
            }
            if (s2 != null) {
                w2 = e2.getWeight() + s2.nextOpen(time, e2.getWeight());
            }

            return w1 - w2;
        }
    }
}


// Class for prioritizing delays.
class DelayComparator implements Comparator<Delay> {

    @Override
    public int compare(Delay o1, Delay o2) {
        return o1.getTime() - o2.getTime();
    }
}

//

