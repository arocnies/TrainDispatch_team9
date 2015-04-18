import graph.Graph;
import graph.GraphFactory;
import org.jfree.ui.RefineryUtilities;

/**
 * Package : PACKAGE_NAME
 * Created by J. Nguy on 4/18/2015.
 */
public class ChartTest{
    public static void main( String[ ] args )
    {
        Graph graph = GraphFactory.generateGraph(args[0]);
        String title = args[0];

        int tests = Integer.parseInt(args[1]);
        long start = System.currentTimeMillis();


        JFreeChartTest chart = new JFreeChartTest("Analysis", title, graph, tests);
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        System.out.println("Tests = " + tests);
        System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0);
    }
}
