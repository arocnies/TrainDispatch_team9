package analysis;

import dispatch.Dispatch;
import dispatch.Plan;
import dispatch.Schedule;
import graph.Graph;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Package : analysis
 * Created by J. Nguy on 4/18/2015.
 */
public class Dataset {
    public XYDataset Dataset( Graph m , int n)
    {
        final XYSeries inputData = new XYSeries( "Trials" );
        double totalAvrDelay = 0;

        for (int i = n; i > 0; i--) {
            Schedule schedule = new Schedule(100, m.getNodes(), 1000);
            Dispatch dispatch = new Dispatch(m);
            Plan plan = dispatch.dispatchTrains(schedule);

            inputData.add( i , plan.getAverageDelay());
            totalAvrDelay += plan.getAverageDelay();
        }


        final XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(inputData);

        System.out.println("Average delay = " + totalAvrDelay / n);

        return dataset;
    }

}

