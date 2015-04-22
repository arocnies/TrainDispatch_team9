/**
 * Created by J. Nguy on 4/21/2015.
*/

import analysis.Calculations;
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
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.*;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;

public class WhiskerTest extends ApplicationFrame
{
    public WhiskerTest( String applicationTitle, String chartTitle, Graph m, int n )
    {

        // Setup.
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trains",
                "Delay",
                createDatasetXY( m , n ),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize(new java.awt.Dimension(1200, 700));
        final XYPlot plot = xylineChart.getXYPlot();
        setContentPane(chartPanel);

        DeviationRenderer deviationrenderer = new DeviationRenderer(true, false);
        deviationrenderer.setSeriesStroke(0, new BasicStroke(3F, 1, 1));
        deviationrenderer.setSeriesFillPaint(0, new Color(255, 200, 200));
        plot.setRenderer(deviationrenderer);
        NumberAxis numberaxis = (NumberAxis)plot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

    }

    private XYDataset createDatasetXY( Graph m , int trials)
    {
        final XYSeries bcSeries = new XYSeries("BaseCase");
        //final XYSeries slSeries  = new XYSeries( "SingleLocking" );
        //final XYSeries opSeries  = new XYSeries( "Optimized" );

        double bcSum = 0;
        //double slSum = 0;
        //double opSum = 0;

        double [] arrayBC = new double[trials];


        // Loops through train amounts.
        for (int i = 0; i <= 500; i += 50) {


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

            arrayBC[i] = 1 / (bcSum / trials);

            bcSeries.add(i, 1 / (bcSum / trials));
            //slSeries.add(i, 1 / (slSum / trials));
            //opSeries.add(i, 1 / (opSum / trials));

        }



        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bcSeries);
        //dataset.addSeries(slSeries);
        //dataset.addSeries(opSeries);



        YIntervalSeries bcYInterval = new YIntervalSeries("BaseCaseY");

        double [] array = new double[trials];

        for (int k = trials; k <= 500; k += 50) {
            double bcSumY = 0;
            //double slSumY = 0;
            //double opSumY = 0;

            // Loop through trails.
            for (int j = trials; j > 0; j--) {
                Schedule bcScheduleY = new Schedule(k, m.getNodes(), 1000);
                //Schedule slSchedule = new Schedule(i, m.getNodes(), 1000);
                //Schedule opSchedule = new Schedule(i, m.getNodes(), 1000);

                Dispatch bcDispatchY = new BaseCaseDispatch(m);
                //Dispatch slDispatch = new Dispatch(m);
                //Dispatch opDispatch = new OptimizedDispatch(m);

                Plan bcPlanY = bcDispatchY.dispatchTrains(bcScheduleY);
                //Plan slPlan = slDispatch.dispatchTrains(slSchedule);
                //Plan opPlan = opDispatch.dispatchTrains(opSchedule);

                bcSumY += bcPlanY.getAverageDelay();
                //slSum += slPlan.getAverageDelay();
                //opSum += opPlan.getAverageDelay();
            }

            double oneOver = 1 / (bcSumY / trials);

            bcYInterval.add(k, oneOver, Calculations.deviationOfUpper(bcSum, trials, arrayBC, oneOver), Calculations.deviationOfLower(bcSum, trials, arrayBC, oneOver));
        }
        YIntervalSeriesCollection yintervalseriescollectionbc = new YIntervalSeriesCollection();
        yintervalseriescollectionbc.addSeries(bcYInterval);

        return yintervalseriescollectionbc;
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
