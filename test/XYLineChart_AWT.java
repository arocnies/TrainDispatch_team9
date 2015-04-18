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
public class XYLineChart_AWT extends ApplicationFrame
{
    public XYLineChart_AWT( String applicationTitle, String chartTitle )
    {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "Trial",
                "Delay",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint(0, Color.RED);

        renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );

        plot.setRenderer( renderer );
        setContentPane( chartPanel );
    }

    private XYDataset createDataset( )
    {
        final XYSeries inputData = new XYSeries( "Trials" );
        inputData.add( 1.0 , 1.0 );
        inputData.add( 2.0 , 4.0 );
        inputData.add( 3.0 , 3.0 );
        inputData.add( 4.0 , 6.0 );
        inputData.add( 5.0 , 6.3 );
        inputData.add( 6.0 , 8.5 );
        inputData.add( 7.0 , 2.4 );
        inputData.add( 8.0 , 5.1 );

        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( inputData);

        return dataset;
    }

    public static void main( String[ ] args )
    {
        XYLineChart_AWT chart = new XYLineChart_AWT("Testing JFreeChart", "This is a title!");
        chart.pack( );
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible( true );
    }
}