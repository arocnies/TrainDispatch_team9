package dispatch;

import graph.*;

import java.util.*;

/**
 * Created by aaron on 4/16/15.
 */

public class OptimizedDispatch extends Dispatch {
    private WaitingEdgeComparator wec = new WaitingEdgeComparator();
    private final int passes;

    public OptimizedDispatch(Graph graph) {
        this(graph, 2);
    }

    public OptimizedDispatch(Graph graph, int runs) {
        super(graph);
        passes = runs;
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


    // ---------------------------------------------------
    @Override
    public Plan dispatchTrains(Schedule schedule) {
        Plan plan = super.dispatchTrains(schedule);
        plan = preOptimize(plan);

        for (int i = 0; i < passes; i++) {
            double prev = plan.getAverageDelay();
            plan = optimize(plan);
            if (plan.getAverageDelay() < prev) {
                System.out.println("+" + (prev - plan.getAverageDelay()));
            }
            else {
                System.out.println("-" + Math.abs(prev - plan.getAverageDelay()));
            }
        }
        return plan;
    }
    // ---------------------------------------------------

    private Itinerary getOptimizedRoute(Train train) {

        // Setup.
//        graph.setPathComparator(new OptimizedPathComparator()); //TODO: Comparator needs more testing.
        Itinerary oldItin = train.getItinerary();
        Itinerary possibleItin = null;

        // Store old itinerary cost. Take into account resource hogging.
        int oldCost = oldItin.getCost() * oldItin.getEdgeNameSet().size();
        int possibleCost;

        // Find cheaper path.
        int tries = 11;
        do {
            // Unlock train.
            Set<String> edgeIds = train.getItinerary().getEdgeNameSet();
            edgeIds.forEach(e -> locks.get(e).evictHolder(train));

            // Try better.
            possibleItin = getItinerary(train, null);
            possibleCost = possibleItin.getCost() * possibleItin.getEdgeNameSet().size();
            tries--;
        }
        while (oldCost < possibleCost && tries > 0);

        // If did better, return better path.
        if (tries > 0) {
            oldItin = possibleItin;
        }
        graph.setPathComparator(new PathComparator());
        return oldItin;
    }

    private Plan preOptimize(Plan plan) {
        List<Train> ts = new ArrayList<>(plan.getTrains());
        Collections.sort(ts, Collections.reverseOrder());
        // Unlock Correction Group.
        for (Train t : ts) {
            Set<String> edgeIds = t.getItinerary().getEdgeNameSet();
            edgeIds.forEach(e -> locks.get(e).evictHolder(t));
        }
        ts.forEach(t -> t.setItinerary(getOptimizedRoute(t)));
        return plan;
    }

    private Plan optimize(Plan plan) {

        // Make list of Delays.
        List<Delay> delays = new LinkedList<>();
        plan.getTrains().forEach(t -> delays.addAll(t.getItinerary().getDelays()));
        delays.sort(new DelayComparator()); // Sort by time. Later ---to---> earlier.

        // Make list of conflict trains.
        Queue<Train> trains = new LinkedList<>();
        for (Delay d : delays) {
            SlotLock<Train> sl = locks.get(d.getEdge().getSharedId());
            Train stopTrain = d.getAffectedTrain();
            Train goTrain = sl.getHolder(d.getTime() + d.getCost() - 1);

            if (!trains.contains(stopTrain)) {
                trains.add(stopTrain);
            }
            if (!trains.contains(goTrain)) {
                trains.add(goTrain);
            }
        }

        // Unlock Correction Group.
        for (Train t : trains) {
            if (t != null) {
                Set<String> edgeIds = t.getItinerary().getEdgeNameSet();
                edgeIds.forEach(e -> locks.get(e).evictHolder(t));
            }
        }
        graph.setPathComparator(new OptimizedPathComparator());
        for (Train t : trains) {
            if (t != null) {
                t.setItinerary(getOptimizedRoute(t));
            }
        }
        graph.setPathComparator(new PathComparator());
        return plan;
    }

    @Deprecated
    private Train rerouteTrain(Train train) {

        // Pre recursion. (Unlock train).
        train.getItinerary().getEdgeNameSet().forEach(e -> locks.get(e).evictHolder(train));
        List<Delay> delays = train.getItinerary().getDelays();

        // BaseCase.
        if (delays.size() == 0) {
            return train;
        }

        delays.forEach(d -> rerouteTrain(d.getAffectedTrain()));
        delays.forEach(d -> routeTrain(d.getAffectedTrain()));
        return train;
    }

    protected void routeTrain(Train train, Edge edge) {
        train.setItinerary(getItinerary(train, edge));
    }

    class WaitingEdgeComparator implements Comparator<Edge> {
        private int time = 0;

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
                w1 = e1.getWeight() * e1.getWeight() + s1.nextOpen(time, e1.getWeight());
            }
            if (s2 != null) {
                w2 = e2.getWeight() * e2.getWeight() + s2.nextOpen(time, e2.getWeight());
            }

            return w1 - w2;
        }
    }

    // Class for prioritizing delays.
    class DelayComparator implements Comparator<Delay> {

        @Override
        public int compare(Delay o1, Delay o2) {
            return o2.getTime() - o1.getTime(); // Later in time delays are better.
        }
    }
}



