/**
 * Created by J. Nguy on 4/18/2015.
 */

import dispatch.BaseCaseDispatch;
import dispatch.Dispatch;
import dispatch.Plan;
import dispatch.Schedule;
import graph.Graph;
import graph.GraphFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;


public class WhiskerTest extends ApplicationFrame
{
    public WhiskerTest( String chartTitle, String title, Graph graph, int tests)
    {
        super(chartTitle);
        JPanel jpanel = createDemoPanel(graph, tests);
        jpanel.setPreferredSize(new Dimension(700, 500));
        setContentPane(jpanel);
    }


/*
    private XYDataset inputSeries(int numOfSeries, Graph m) {

        // Default parameters
        String[] seriesNames = {"BaseCase", "SingleLocking", "Optimized"};
        int maxTrains = 500;
        int samples = 500;
        Dispatch[] dispatches = {new BaseCaseDispatch(m), new Dispatch(m), new OptimizedDispatch(m)};


        int k = 0;
        final XYSeriesCollection dataset = new XYSeriesCollection();

        for (int i = 0; i < numOfSeries; i++) {
            XYSeries series1 = createSeries(seriesNames[k], dispatches[k], m, maxTrains, samples);
            dataset.addSeries(series1);
            k++;
        }

        return dataset;
    }

    private XYSeries createSeries(String name, Dispatch dispatch, Graph graph, int maxTrains, int samples/*, int trials) {
        final XYSeries series = new XYSeries(name);

        // Loops through train amounts.
        for (int i = 1; i <= maxTrains; i += (maxTrains / samples)) {
            Schedule schedule = new Schedule(i, graph.getNodes(), 1000);
            Plan plan = dispatch.dispatchTrains(schedule);
            series.add(i, plan.getAverageDelay());
        }

        return series;
    }

*/

     static XYDataset createDataset( Graph m , int trials) {

         final YIntervalSeries bcSeries = new YIntervalSeries( "BaseCase" );
         //final XYSeries slSeries  = new XYSeries( "SingleLocking" );
         //final XYSeries opSeries  = new XYSeries( "Optimized" );

         // Loops through train amounts.
         for (int i = 1; i <= 100; i += 500/500) {

             double bcSum = 0;
             //double slSum = 0;
             //double opSum = 0;

             // Loop through trails.
             for (int j = trials; j > 0; j--) {
                 Schedule bcSchedule = new Schedule(i, m.getNodes(), 1000);
                 //Schedule slSchedule = new Schedule(i, m.getNodes(), 1000);
                 //Schedule opSchedule = new Schedule(i, m.getNodes(), 1000);

                 Dispatch bcDispatch = new BaseCaseDispatch(m);
                 //Dispatch slDispatch = new Dispatch(m);
                 //Dispatch opDispatch = new OptimizedDispatch(m);

                 Plan bcPlan = bcDispatch.dispatchTrains(bcSchedule);
                 //Plan slPlan = slDispatch.dispatchTrains(slSchedule);
                 //Plan opPlan = opDispatch.dispatchTrains(opSchedule);

                 bcSum += bcPlan.getAverageDelay();
                 //slSum += slPlan.getAverageDelay();
                 //opSum += opPlan.getAverageDelay();
             }
             double oneOver = 1 / (bcSum / trials);
             bcSeries.add(i, oneOver, oneOver + 1, oneOver - 1);
             //slSeries.add(i, 1 / (slSum / trials));
             //opSeries.add(i, 1 / (opSum / trials));
         }


         final YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
         dataset.addSeries(bcSeries);
         //dataset.addSeries(slSeries);
         //dataset.addSeries(opSeries);

         return dataset;
     }

    private static JFreeChart createChart(XYDataset xydataset, Graph m, int n) {

        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
                "Projected Values - Test",
                "Date",
                "Index Projection",
                createDataset(m, n),
                true, true, false);

        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        DeviationRenderer deviationrenderer = new DeviationRenderer(true, false);
        deviationrenderer.setSeriesStroke(0, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesStroke(0, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesStroke(1, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesFillPaint(0, new Color(255, 200, 200));
        deviationrenderer.setSeriesFillPaint(1, new Color(200, 200, 255));
        xyplot.setRenderer(deviationrenderer);
        NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        return jfreechart;
    }


    public static JPanel createDemoPanel(Graph m, int n) {
        JFreeChart jfreechart = createChart(createDataset(m, n), m, n);
        return new ChartPanel(jfreechart);
    }


    public static void main( String[ ] args )
    {

        Graph graph = GraphFactory.generateGraph(args[0]);
        String title = args[0];

        int tests = Integer.parseInt(args[1]);
        long start = System.currentTimeMillis();


        WhiskerTest chart = new WhiskerTest("Analysis", title, graph, tests);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);

        System.out.println("Tests = " + tests);
        System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0);
    }
}
