package dispatch;

import graph.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by aaron on 4/6/15.
 */

public class Schedule {

    private final List<Train> trains;
    private final List<Node> nodes;
    private final int cutOffTime;
    private final Random rng;

    public Schedule(int trainAmount, List<Node> nodeList, int time) {
        trains = new LinkedList<>();
        cutOffTime = time;
        nodes = nodeList;
        rng = new Random();

        for (int i = 0; i < trainAmount; i++) {
            trains.add(generateTrain(nodes));
        }
    }

    private Train generateTrain(List<Node> nodes) {
        return new Train(nextNode(), nextNode(), nextTime());
    }

    private int nextTime() {
        return rng.nextInt(cutOffTime);
    }

    private Node nextNode() {
        return nodes.get(rng.nextInt(nodes.size()));
    }

    public List<Train> getTrains() {
        return trains;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public int getCutOffTime() {
        return cutOffTime;
    }
}
