package graph;

import java.util.Comparator;

/**
 * Created by aaron on 4/16/15.
 */


/*
TODO:
Use this to compare edge values instead of just weight for different optimizations.
In Dispatch, have the optimized Dispatch pass a EdgeComparator that uses the getWait()
from dispatch to consider the trains and delays when finding the path.
 */
public class EdgeComparator implements Comparator<Edge>{

    @Override
    public int compare(Edge e1, Edge e2) {
        return e1.getWeight() - e2.getWeight();
    }
}
