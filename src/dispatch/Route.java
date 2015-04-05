package dispatch;

import graph.Edge;
import graph.Node;

import java.util.List;

/**
 * Created by aaron on 4/5/15.
 */

public interface Route {

    int getCost();

    List<Node> getNodes();

    List<Edge> getEdges();
}
