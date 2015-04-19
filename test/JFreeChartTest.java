/**
 * Created by J. Nguy on 4/18/2015.
 */
import dispatch.*;
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

        // Setup.
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trains",
                "Delay",
                createDataset( m , n ),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1200 , 700 ) );
        final XYPlot plot = xylineChart.getXYPlot();
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//
//        // Sets the series to color RED
//        renderer.setSeriesPaint(0, Color.RED);
//
//        //  Removes the boxes for the data points
//        renderer.setSeriesShapesVisible(0, false);
//
//        // Thickness of the lines rendered
//        renderer.setSeriesStroke( 0 , new BasicStroke( 1.0f ) );
//
//        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( Graph m , int trials)
    {
        final XYSeries bcSeries = new XYSeries( "BaseCase" );
        final XYSeries slSeries  = new XYSeries( "SingleLocking" );
        final XYSeries opSeries  = new XYSeries( "Optimized" );

        // Loops through train amounts.
        for (int i = 0; i <= 1000; i += 50) {

            double bcSum = 0;
            double slSum = 0;
            double opSum = 0;

            // Loop through trails.
            for (int j = trials; j > 0; j--) {
                Schedule bcSchedule = new Schedule(i, m.getNodes(), 1000);
                Schedule slSchedule = new Schedule(i, m.getNodes(), 1000);
                Schedule opSchedule = new Schedule(i, m.getNodes(), 1000);

                Dispatch bcDispatch = new BaseCaseDispatch(m, bcSchedule.getDuration());
                Dispatch slDispatch = new Dispatch(m, slSchedule.getDuration());
                Dispatch opDispatch = new OptimizedDispatch(m, opSchedule.getDuration());

                Plan bcPlan = bcDispatch.dispatchTrains(bcSchedule);
                Plan slPlan = slDispatch.dispatchTrains(slSchedule);
                Plan opPlan = opDispatch.dispatchTrains(opSchedule);

                bcSum += bcPlan.getAverageDelay();
                slSum += slPlan.getAverageDelay();
                opSum += opPlan.getAverageDelay();
            }
            bcSeries.add(i, 1 / (bcSum / trials));
            slSeries.add(i, 1 / (slSum / trials));
            opSeries.add(i, 1 / (opSum / trials));
        }


        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bcSeries);
        dataset.addSeries(slSeries);
        dataset.addSeries(opSeries);

        return dataset;
    }



    public static void main( String[ ] args )
    {
        Graph graph = GraphFactory.generateGraph(args[0]);
        String title = args[0];

        int tests = Integer.parseInt(args[1]);
        long start = System.currentTimeMillis();


        JFreeChartTest chart = new JFreeChartTest("Analysis", title, graph, tests);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        System.out.println("Tests = " + tests);
        System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0);
    }
}
