import dispatch.*;
import graph.Graph;
import graph.GraphFactory;

/**
 * Created by Aaron on 4/12/2015.
 */

public class PerformanceTest {
    public static void main(String[] args) {

        int tests = Integer.parseInt(args[1]);
        double totalAvrDelay = 0;
        long start = System.currentTimeMillis();

        Graph graph = GraphFactory.generateGraph(args[0]);
        for (int i = tests; i > 0; i--) {
            Schedule schedule = new Schedule(300, graph.getNodes(), 1000);
            Dispatch dispatch = new Dispatch(graph);
            Plan plan = dispatch.dispatchTrains(schedule);

            totalAvrDelay += plan.getAverageDelay();
        }
        System.out.println("Tests = " + tests );
        System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0 );
        System.out.println("Average delay = " + totalAvrDelay / tests);
    }
}
