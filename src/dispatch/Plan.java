package dispatch;

import graph.Graph;

import java.util.List;
import java.util.Set;

/**
 * Created by aaron on 4/6/15.
 */

public class Plan {

    List<Train> trains;

    public Plan(List<Train> listOfTrains) {
        trains = listOfTrains;
    }

    public List<Train> getTrains() {
        return trains;
    }

    public double getAverageDelay() {
        double totalDelay = 0;
        for (Train t : trains) {
            totalDelay += t.getDelay();
        }
        return totalDelay / trains.size();
    }
}
