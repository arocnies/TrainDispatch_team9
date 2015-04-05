package dispatch;

import graph.Edge;
import graph.Node;
import graph.Path;

import java.util.*;

/**
 * Created by aaron on 4/4/15.
 */

public class Route {
    private final List<RouteElement> elements = new LinkedList<>();
    private final List<Delay> delays = new LinkedList<>();
    private final List<Path> paths= new LinkedList<>();
    private int totalCost;

    void addElement(RouteElement re) {
        elements.add(re);
        totalCost += re.getCost();
        if (re instanceof Delay) {
            delays.add((Delay) re);
        }
        else if (re instanceof Path) {
            paths.add((Path) re);
        }
    }

    public List<Delay> getDelays() {
        return delays;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public List<Node> getNodes() {
        Set<Node> nodes = new HashSet<>();

        for (RouteElement re : elements) {
            nodes.addAll(re.getNodes());
        }

        return new ArrayList<>(nodes);
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new LinkedList<>();
        for (Path p : getPaths()) {
            edges.addAll(p.getEdges());
        }
        return edges;
    }
}
