/**
 * Created by J. Nguy on 4/18/2015.
 */

import analysis.Calculations;
import dispatch.*;
import graph.Graph;
import graph.GraphFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class JFreeChartTest extends ApplicationFrame
{
    public JFreeChartTest( String applicationTitle, String chartTitle, Graph m, int n, int trains, int samples )
    {

        // Setup.
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trains",
                "Performance",
                createDataset(m, n, trains, samples),
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

    private XYSeries createSeries(String name, Dispatch dispatch, Graph graph, int maxTrains, int samples) {
        final XYSeries series = new XYSeries(name);

        // Loops through train amounts.
        for (int i = 1; i <= maxTrains; i += (maxTrains / samples)) {
            Schedule schedule = new Schedule(i, graph.getNodes(), 1000);
            Plan plan = dispatch.dispatchTrains(schedule);
            series.add(i, plan.getAverageDelay());
        }

        return series;
    }

    private XYDataset createDataset( Graph m , int trials, int trains, int samples)
    {
        final XYSeries bcSeries = new XYSeries( "BaseCase" );
        final XYSeries slSeries  = new XYSeries( "SingleLocking" );
        final XYSeries opSeries  = new XYSeries( "Optimized" );
        int trainsPlus = trains + 1;

        double[] bcFileOut = new double[trainsPlus];
        double[] slFileOut = new double[trainsPlus];
        double[] opFileOut = new double[trainsPlus];

        double[] bcResult = new double[trainsPlus];
        double[] slResult = new double[trainsPlus];
        double[] opResult = new double[trainsPlus];

        double[] bcPercent = new double[trainsPlus];
        double[] slPercent = new double[trainsPlus];
        double[] opPercent = new double[trainsPlus];

        // Loops through train amounts.
        for (int i = 1; i <= trains; i += trains / samples) {

            double bcSum = 0;
            double slSum = 0;
            double opSum = 0;

            double[] bcArray = new double[trials];
            double[] slArray = new double[trials];
            double[] opArray = new double[trials];


            int k = 0;

            // Loop through trails.
            for (int j = trials; j > 0; j--) {
                Schedule bcSchedule = new Schedule(i, m.getNodes(), 1000);
                Schedule slSchedule = new Schedule(i, m.getNodes(), 1000);
                Schedule opSchedule = new Schedule(i, m.getNodes(), 1000);

                Dispatch bcDispatch = new BaseCaseDispatch(m);
                Dispatch slDispatch = new Dispatch(m);
                Dispatch opDispatch = new OptimizedDispatch(m);

                Plan bcPlan = bcDispatch.dispatchTrains(bcSchedule);
                Plan slPlan = slDispatch.dispatchTrains(slSchedule);
                Plan opPlan = opDispatch.dispatchTrains(opSchedule);

                bcArray[k] = bcPlan.getDelayCount();
                slArray[k] = slPlan.getDelayCount();
                opArray[k] = opPlan.getDelayCount();

                k++;

                bcSum += bcPlan.getAverageDelay();
                slSum += slPlan.getAverageDelay();
                opSum += opPlan.getAverageDelay();
            }
            bcSeries.add(i, 1 / (bcSum / trials));
            slSeries.add(i, 1 / (slSum / trials));
            opSeries.add(i, 1 / (opSum / trials));

            bcResult[i] = 1 / (bcSum / trials);
            slResult[i] = 1 / (slSum / trials);
            opResult[i] = 1 / (opSum / trials);

            double bc = Calculations.deviation(bcArray);
            double sl = Calculations.deviation(slArray);
            double op = Calculations.deviation(opArray);

            bcPercent[i] = (bc / (1 / (bcSum / trials)));
            slPercent[i] = (sl / (1 / (slSum / trials)));
            opPercent[i] = (op / (1 / (opSum / trials)));

            bcFileOut[i] = bc;
            slFileOut[i] = sl;
            opFileOut[i] = op;


        }


        // Output the standard deviation results to a text file for each dispatch
        try {
            PrintStream bcOut = new PrintStream(new FileOutputStream("StdDev_BaseCase.txt"));
            PrintStream slOut = new PrintStream(new FileOutputStream("StdDev_SingleLocking.txt"));
            PrintStream opOut = new PrintStream(new FileOutputStream("StdDev_Optimized.txt"));
            bcOut.printf("Base Case Results %nNumber of Trains        Result       StdDev%n");
            slOut.printf("S Locking Results %nNumber of Trains        Result       StdDev%n");
            opOut.printf("Optimized Results %nNumber of Trains        Result       StdDev%n");
            for (int i = 0; i < trains; i++) {
                bcOut.printf("%14d%15.3f%11.3s%% %n", i, bcResult[i], bcFileOut[i]);
                slOut.printf("%14d%15.3f%11.3s%% %n", i, slResult[i], slFileOut[i]);
                opOut.printf("%14d%15.3f%11.3s%% %n", i, opResult[i], opFileOut[i]);
            }
            bcOut.close();
            slOut.close();
            opOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bcSeries);
        dataset.addSeries(slSeries);
        dataset.addSeries(opSeries);

        return dataset;
    }

public static void JFreeCreation(String graphName, String titleIn, String testsIn, String trainsIn, String samplesIn) {
    Graph graph = GraphFactory.generateGraph(graphName);
    String title = titleIn;

    int tests = Integer.parseInt(testsIn);
    long start = System.currentTimeMillis();
    int trains = Integer.parseInt(trainsIn);
    int samples = Integer.parseInt(samplesIn);


    JFreeChartTest chart = new JFreeChartTest("Analysis", title, graph, tests, trains, samples);
    chart.pack();
    RefineryUtilities.centerFrameOnScreen(chart);
    chart.setVisible(true);

    System.out.println("Tests = " + tests);
    System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0);
}

    public static void main( String[ ] args )
    {
        JFreeCreation(args[0], args[0], args[1], args[2], args[3]);
    }
}
