package dispatch;

import graph.Edge;
import graph.Node;
import graph.Path;

import java.util.*;

/**
 * Created by aaron on 4/4/15.
 */

public class Itinerary implements Routable, Comparable<Routable> {

    private final List<Routable> elements = new LinkedList<>();
    private final List<Path> paths= new LinkedList<>();
    private final List<Delay> delays = new LinkedList<>();
    private int totalCost;

    private void addElement(Routable r) {
        elements.add(r);
        totalCost += r.getCost();
    }

    int addPath(Path path) {
        addElement(path);
        paths.add(path);
        return path.getCost();
    }

    int addDelay(Delay delay) {
        addElement(delay);
        delays.add(delay);
        return delay.getCost();
    }

    public List<Routable> getElements() {
        return elements;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public List<Delay> getDelays() {
        return delays;
    }

    @Override
    public int getCost() {
        return totalCost;
    }

    @Override
    public List<Node> getNodes() {
        Set<Node> nodes = new HashSet<>();

        for (Routable r : paths) {
            nodes.addAll(r.getNodes());
        }

        return new ArrayList<>(nodes);
    }

    @Override
    public List<Edge> getEdges() {
        Set<Edge> edges = new HashSet<>();
        for (Routable r : getPaths()) {
            edges.addAll(r.getEdges());
        }
        return new ArrayList<>(edges);
    }

    @Override
    public int compareTo(Routable o) {
        return this.getCost() - o.getCost();
    }
}
