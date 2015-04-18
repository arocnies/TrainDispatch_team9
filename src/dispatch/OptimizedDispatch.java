package dispatch;

import graph.Edge;
import graph.Graph;
import graph.Path;
import javafx.collections.transformation.SortedList;

import java.util.*;

/**
 * Created by aaron on 4/16/15.
 */

public class OptimizedDispatch extends Dispatch {

    public OptimizedDispatch(Graph graph, int duration) {
        super(graph, duration);
    }

    @Override
    public Plan dispatchTrains (Schedule schedule) {
        Plan plan = super.dispatchTrains(schedule);
        double rawDelay = plan.getAverageDelay();

        // Build delay queue.
        List<Delay> delays = new LinkedList<>();
        for (Train t : plan.getTrains()) {
            t.getItinerary().getDelays().forEach(delays::add);
        }

        delays.sort(new DelayCostComparator());

        plan.getTrains().forEach(this::unlock);
//        for (Delay d : delays) {
//            routeTrain(d.getAffectedTrain());
//        }

        for (Delay d : delays) {
            Train train = d.getAffectedTrain();
            Itinerary raw = train.getItinerary();
            unlock(train);
//            routeTrain(train);
            routeTrain(train, d.getEdge());
            if (train.getItinerary().getCost() > raw.getCost()) {
                System.out.print("IT'S WORSE! Rerouting\n");
                unlock(train);
                routeTrain(train);
            }
        }

        System.out.println("\nRAW = " + rawDelay + " Optimized = " + plan.getAverageDelay() + "\n");
        return plan;
    }
}



class DelayCostComparator implements Comparator<Delay> {

    @Override
    public int compare(Delay o1, Delay o2) {
        return o1.getCost() - o2.getCost();
    }
}
