import dispatch.Dispatch;
import dispatch.Plan;
import dispatch.Schedule;
import graph.Graph;
import graph.GraphFactory;

/**
 * Created by Aaron on 4/11/2015.
 */

public class DispatchTest {
    public static void main(String[] args) {

        Graph graph = GraphFactory.generateGraph("northern_rail_map.graph");
        Schedule schedule = new Schedule(10, graph.getNodes(), 1000);
        Dispatch dispatch = new Dispatch(graph, schedule.getDuration());

        Plan plan = dispatch.dispatchTrains(schedule);


    }
}
