package dispatch;

import graph.Edge;
import graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron on 4/4/15.
 */

public class Delay extends RouteElement {
    private Node node;
    private Edge edge;
    private Train runningTrain;
    private Train delayedTrain;


    @Override
    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        nodes.add(node);
        return nodes;
    }
}
