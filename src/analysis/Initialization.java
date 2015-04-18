package analysis;

import graph.Graph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.ApplicationFrame;

import java.awt.*;

/**
 * Package : analysis
 * Created by J. Nguy on 4/18/2015.
 */
public class Initialization extends ApplicationFrame{
    public Initialization( String applicationTitle, String chartTitle, Graph m, int n )
    {
        super(applicationTitle);
        Dataset dataset = new Dataset();
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trial",
                "Delay",
                dataset.Dataset(m, n),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 1000 , 700 ) );
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

}
