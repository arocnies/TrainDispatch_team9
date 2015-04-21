package graph;

import java.util.Comparator;

/**
 * Created by Aaron on 4/21/2015.
 */
public class PathComparator implements Comparator<Path> {
    @Override
    public int compare(Path o1, Path o2) {
        return o1.getCost() - o2.getCost();
    }
}
