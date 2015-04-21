package graph;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Graph {

    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final Map<Node, Map<Node, Path>> directPaths; // A map from each node to it's minimum paths.
    private Comparator<Edge> ec = new EdgeComparator();

    /**
     * Constructor for new graph using the provided nodes.
     * @param nodes Array of Nodes
     */
    public Graph(Set<Node> nodes) {
        this.nodes = nodes;

        // Fill edges list with all edges.
        edges = new HashSet<>();
        nodes.forEach(n -> edges.addAll(n.getEdges()));

        // Initialize direct path map.
        directPaths = new HashMap<>(nodes.size());
        // For each key, put empty hash map sized for each node.
        nodes.forEach(n -> directPaths.put(n, new HashMap<>(nodes.size())));
    }

    /**
     * Returns a Path object leading from startNode to endNode found using Dijkstra's algorithm.
     * @param startNode The node of origin.
     * @param endNode The final destination node.
     * @param excludedEdge An edge to exclude from the calculation.
     * @return Path
     */
    public Path getPath(Node startNode, Node endNode, Edge excludedEdge) {

        Path retPath;

        // If already there...
        if (startNode == endNode) {
            retPath = new Path(startNode);
        }
        else {

            // If direct path is not known.
            retPath = directPaths.get(startNode).get(endNode);
            if (retPath == null) {

                // Minimum distance from startNode to key node.
                Map<Node, Path> minPaths = new HashMap<>(nodes.size());
                for (Node n : nodes) {
                    minPaths.put(n, new Path(startNode));
                }

                // Path to return after it has been filled with edges.
                Path path = new Path(startNode);

                // Priority queue by weight.
                PriorityQueue<Path> priorityQueue = new PriorityQueue<>();
                priorityQueue.add(path);

                while (!priorityQueue.isEmpty()) {
                    Path currentPath = priorityQueue.poll();
                    Set<Edge> availableEdges = currentPath.getLastNode().getEdges();

                    // Look at each edge of the current node.
                    for (Edge edge : availableEdges) {

                        // If not excluded edge and it isn't the only edge.
                        if (edge != excludedEdge || availableEdges.size() > 1) {
                            Node nextNode = edge.getEnd();

                            // If nextNode is not the last node (no backtracking).
                            if (nextNode != currentPath.getLastEdge().getStart()) {

                                // If my current nodes distance is shorter than stored distance.
                                int currentDistance = currentPath.getCost()
                                        + ec.compare(edge, minPaths.get(nextNode).getLastEdge());
                                int knownDistance = minPaths.get(nextNode).getCost()
                                        + ec.compare(minPaths.get(nextNode).getLastEdge(), edge);
                                if (currentDistance < knownDistance || knownDistance < 0) {

                                    Path betterPath = new Path(currentPath);
                                    betterPath.addEdge(edge);

                                    // Replace old minPath in queue with current path with edge.
                                    priorityQueue.remove(minPaths.get(nextNode));
                                    priorityQueue.add(betterPath);
                                    minPaths.put(betterPath.getLastNode(), betterPath);
                                }
                            }
                        }
                    }
                } // End search loop.

                // Found shortest paths, store result.
                directPaths.put(startNode, minPaths);

                retPath = minPaths.get(endNode);
            }
        }
        return retPath;
    }

    public void setEdgeComparator(Comparator<Edge> comparator) {
        ec = comparator;
    }

    public Path getPath(Node startNode, Node endNode) {
        return getPath(startNode, endNode, null);
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Loop through all nodes
        for (Node node: nodes) {
            sb.append("[" + node + "]");

            for (Edge edge : node.getEdges()) {
                sb.append(" (" + edge.getWeight() + ", " + edge.getEnd() + ")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
