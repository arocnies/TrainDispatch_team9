import dispatch.*;
import graph.Graph;
import graph.GraphFactory;
import visualization.DisplayGraph;

/**
 * Created by Aaron on 4/11/2015.
 */

public class DispatchTest {
    public static void main(String[] args) {

        Graph graph = GraphFactory.generateGraph("res/northern_rail_map.graph");
        System.out.println(graph);

        Schedule schedule = new Schedule(30, graph.getNodes(), 1000);
        System.out.println(schedule);

        Dispatch dispatch = new Dispatch(graph, schedule.getDuration());

        Plan plan = dispatch.dispatchTrains(schedule);
        DisplayGraph dg = new DisplayGraph(graph);
        dg.display();

        // Paint all the paths from all the trains.
        for (Train t : plan.getTrains()) {
            t.getItinerary().getPaths().forEach(p -> dg.paint(p, false));
        }

        // Paint delays.
        for (Train t : plan.getTrains()) {
            t.getItinerary().getDelays().forEach(dg::paint);
        }

        System.out.println(plan.getAverageDelay());
    }
}
