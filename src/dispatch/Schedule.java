package dispatch;

import graph.Node;

import java.util.*;

/**
 * Created by aaron on 4/6/15.
 */

public class Schedule {

    private final Set<Train> trains;
    private final Set<Node> nodes;
    private final int duration;
    private final Random rng;

    public Schedule(int trainAmount, Set<Node> nodeList, int time) {
        trains = new HashSet<>();
        duration = time;
        nodes = nodeList;
        rng = new Random();

        for (int i = 0; i < trainAmount; i++) {
            trains.add(generateTrain());
        }
    }

    private Train generateTrain() {
        Node start = nextNode();
        Node end = nextNode();
        while (start == end) {
            end = nextNode();
        }
        return new Train(nextNode(), nextNode(), nextTime());
    }

    private int nextTime() {
        return rng.nextInt(duration);
    }

    private Node nextNode() {
        Node retNode = null;
        int randomIndex = rng.nextInt(nodes.size());
        for (Node n : nodes) {
            if (randomIndex == 0) {
                retNode = n;
            }
            randomIndex--;
        }
        return retNode;
    }

    public Set<Train> getTrains() {
        return trains;
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Train t : getTrains()) {
            sb.append(i + ": " + t.getStart() + " -> " + t.getEnd());
            sb.append("\n");
            i++;
        }
        return sb.toString();
    }
}
