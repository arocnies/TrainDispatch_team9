/**
 * Created by J. Nguy on 4/18/2015.
 */
import dispatch.AbstractDispatch;
import dispatch.Dispatch;
import dispatch.Plan;
import dispatch.Schedule;
import graph.Graph;
import graph.GraphFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

/**
 * Package : PACKAGE_NAME
 * Created by J. Nguy on 4/18/2015.
 */
public class JFreeChartTest extends ApplicationFrame
{
    public JFreeChartTest( String applicationTitle, String chartTitle, Graph m, int n )
    {
        super(applicationTitle);

        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trial",
                "Delay",
                createDataset( m , n ),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1200 , 700 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );

        // Sets the series to color RED
        renderer.setSeriesPaint(0, Color.RED);

        //  Removes the boxes for the data points
        renderer.setSeriesShapesVisible(0, false);

        // Thickness of the lines rendered
        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );

        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( Graph m , int n)
    {
        final XYSeries inputData = new XYSeries( "Trials" );
        double totalAvrDelay = 0;

        for (int i = n; i > 0; i--) {
            Schedule schedule = new Schedule(100, m.getNodes(), 1000);
            AbstractDispatch dispatch = new Dispatch(m, schedule.getDuration());
            Plan plan = dispatch.dispatchTrains(schedule);

            inputData.add( i , plan.getAverageDelay());
            totalAvrDelay += plan.getAverageDelay();
        }


        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( inputData);

        System.out.println("Average delay = " + totalAvrDelay / n);

        return dataset;
    }



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