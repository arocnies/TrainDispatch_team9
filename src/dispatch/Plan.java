package dispatch;

import java.util.Set;

/**
 * Created by aaron on 4/6/15.
 */

public class Plan {

    Set<Train> trains;

    public Plan(Schedule schedule) {
        trains = schedule.getTrains();
    }

    public Set<Train> getTrains() {
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
