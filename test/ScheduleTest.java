import dispatch.Schedule;
import dispatch.Train;
import graph.Graph;
import graph.GraphFactory;

/**
 * Created by Aaron on 4/12/2015.
 */

public class ScheduleTest {
    public static void main(String[] args) {
        Graph graph = GraphFactory.generateGraph("res/northern_rail_map.graph");
        Schedule schedule = new Schedule(10, graph.getNodes(), 100);
        System.out.println(schedule);
    }
}
