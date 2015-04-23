/**
 * Package : PACKAGE_NAME
 * Created by J. Nguy on 4/22/2015.
 */
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space

import analysis.Calculations;
import dispatch.*;
import graph.Graph;
import graph.GraphFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.DeviationRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class DeviationRendererDemo1 extends ApplicationFrame
{

    public DeviationRendererDemo1(String s, Graph graph, int tests)
    {
        super(s);
        JPanel jpanel = createDemoPanel(s, graph, tests);
        jpanel.setPreferredSize(new Dimension(1200, 700));
        setContentPane(jpanel);
    }

    private static XYDataset createDataset(Graph m, int trials)
    {
        YIntervalSeries baseSeries = new YIntervalSeries("BaseCase");
        YIntervalSeries singleSeries = new YIntervalSeries("SingleLocking");
        YIntervalSeries optiSeries = new YIntervalSeries("Optimized");

        double d = 100D;
        double d1 = 100D;
        double d4 = 100D;

        for (int i = 0; i <= 50; i += 500/500)
        {

            double bcSum = 0;
            double slSum = 0;
            double opSum = 0;

            double[] bcArray = new double[trials];
            double[] slArray = new double[trials];
            double[] opArray = new double[trials];

            double bcUpper = 0;
            double slUpper = 0;
            double opUpper = 0;

            double bcLower = 0;
            double slLower = 0;
            double opLower = 0;

            int k = 0;

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


/*
            d = (d + Math.random()) - 0.47999999999999998D;
            double d2 = 0.050000000000000003D * (double) i;
            if (i == 50)
                baseSeries.add(i, d, d - d2, d + d2);
            else
                baseSeries.add(i, d, d - d2, d + d2);


            d1 = (d1 + Math.random()) - 0.5D;
            double d3 = 0.070000000000000007D * (double)i;
            singleSeries.add(i, d1, d1 - d3, d1 + d3);


            d4 = (d4 + Math.random()) - 0.5111111222222222D;
            double d5 = 0.070000000000000007D * (double)i;
            optiSeries.add(i, d4, d4 - d5, d4 + d5);
     ***************************************************************/
            double bcOver = 1 / (bcSum / trials);
            double slOver = 1 / (slSum / trials);
            double opOver = 1 / (opSum / trials);

            bcUpper = Calculations.deviation(bcArray);
            slUpper = Calculations.deviation(slArray);
            opUpper = Calculations.deviation(opArray);

            bcLower = Calculations.deviation(bcArray);
            slLower = Calculations.deviation(slArray);
            opLower = Calculations.deviation(opArray);


            baseSeries.add(i, bcOver, bcUpper, bcLower);
            singleSeries.add(i, slOver, slUpper, slLower);
            optiSeries.add(i, opOver, opUpper, opLower);
/**/
        }

        YIntervalSeriesCollection yintervalseriescollection = new YIntervalSeriesCollection();
        yintervalseriescollection.addSeries(baseSeries);
        yintervalseriescollection.addSeries(singleSeries);
        yintervalseriescollection.addSeries(optiSeries);
        return yintervalseriescollection;
    }

    private static JFreeChart createChart(XYDataset xydataset, String title, Graph graph, int tests)
    {
        JFreeChart jfreechart = ChartFactory.createXYLineChart(title,
                "Trains",
                "Performance",
                xydataset,
                PlotOrientation.VERTICAL,
                true, true, false);
        //jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        //xyplot.setBackgroundPaint(Color.lightGray);
        //xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        //xyplot.setDomainGridlinePaint(Color.white);
        //xyplot.setRangeGridlinePaint(Color.white);
        DeviationRenderer deviationrenderer = new DeviationRenderer(true, false);
        deviationrenderer.setSeriesStroke(0, new BasicStroke(1F, 1, 1));
        deviationrenderer.setSeriesStroke(0, new BasicStroke(1F, 1, 1));
        deviationrenderer.setSeriesStroke(1, new BasicStroke(1F, 1, 1));
        deviationrenderer.setSeriesStroke(2, new BasicStroke(1F, 1, 1));
        deviationrenderer.setSeriesFillPaint(0, new Color(255, 200, 200));
        deviationrenderer.setSeriesFillPaint(1, new Color(200, 200, 255));
        deviationrenderer.setSeriesFillPaint(2, new Color(200, 255, 200));
        xyplot.setRenderer(deviationrenderer);
        NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        return jfreechart;
    }

    public static JPanel createDemoPanel(String title, Graph graph, int tests)
    {
        JFreeChart jfreechart = createChart(createDataset(graph, tests), title, graph, tests);
        return new ChartPanel(jfreechart);
    }

    public static void main(String args[])
    {
        Graph graph = GraphFactory.generateGraph(args[0]);
        String title = args[0];

        int tests = Integer.parseInt(args[1]);
        long start = System.currentTimeMillis();


        DeviationRendererDemo1 deviationrendererdemo1 = new DeviationRendererDemo1(title, graph, tests);
        deviationrendererdemo1.pack();
        RefineryUtilities.centerFrameOnScreen(deviationrendererdemo1);
        deviationrendererdemo1.setVisible(true);

        //System.out.println("Tests = " + tests);
        //System.out.println("Runtime = " + (System.currentTimeMillis() - start) / 1000.0);
    }
}