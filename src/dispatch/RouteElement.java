package dispatch;

import graph.Node;

import java.util.List;

/**
 * Created by aaron on 4/4/15.
 */

public abstract class RouteElement {
    protected int cost;

   public int getCost() {
       return cost;
   }

    public abstract List<Node> getNodes();
}
