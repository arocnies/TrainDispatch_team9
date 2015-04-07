package graph;

import java.util.Set;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Node {
    private final String name;
    private Set<Edge> edges;

    public Node() {
        name = nextName();
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    void setEdges(Set<Edge> edgeArray) {
        edges = edgeArray;
    }

    // Provides name scheme for nodes.
    private static int nodeInstanceCount;
    private static String nextName() {

        StringBuilder name = new StringBuilder();
        int n = nodeInstanceCount + 65;

        while (n > 90) {
            name.append(Character.toChars((n % 90) + 64));
            n -= 26;
        }

        name.append(Character.toChars(n));
        nodeInstanceCount++;
        return name.toString();
    }

    @Override
    public String toString() {
        return name;
    }
}
