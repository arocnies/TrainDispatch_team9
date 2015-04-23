import dispatch.*;
import graph.Graph;
import graph.GraphFactory;
import visualization.DisplayGraph;

/**
 * Created by Aaron on 4/11/2015.
 */

public class DispatchTest {
    public static void DispatchVisualization(String graphIn, String whichCase) {
        Graph graph = GraphFactory.generateGraph(graphIn);
        System.out.println(graph);

        Schedule schedule = new Schedule(30, graph.getNodes(), 1);
        System.out.println(schedule);


        int input = Integer.parseInt(whichCase);
        if (input == 1) {

            Dispatch dispatch = new BaseCaseDispatch(graph);
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


        } else if (input == 2) {
            Dispatch dispatch = new Dispatch(graph);
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


        } else {
            Dispatch dispatch = new OptimizedDispatch(graph);
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
        }
    }
    public static void main(String[] args) {
        DispatchVisualization(args[0], args[1]);
    }
}
