package dispatch;

import graph.Path;
import graph.PathComparator;

/**
 * Created by Aaron on 4/21/2015.
 */

public class OptimizedPathComparator extends PathComparator {

    @Override
    public int compare(Path o1, Path o2) {
//        return -1;
//        return o2.getCost() - o1.getCost();
//        return o1.getEdgeNameSet().size() - o2.getEdgeNameSet().size();
//        return (o1.getCost() + o1.getEdgeNameSet().size()) - (o2.getCost() + o2.getEdgeNameSet().size());
        return o1.getCost() * o1.getEdgeNameSet().size() - o2.getCost() * o2.getEdgeNameSet().size();
    }
}
