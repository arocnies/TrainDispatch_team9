package graph;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Graph {

    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final Map<Node, Map<Node, Path>> directPaths; // A map from each node to it's minimum paths.

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
        // For each key, put emtpy hash map sized for each node.
        nodes.forEach(n -> directPaths.put(n, new HashMap<>(nodes.size())));
    }

    /**
     * Returns a Path object leading from startNode to endNode found using Dijkstra's algorithm.
     * @param startNode The node of origin.
     * @param endNode The final destination node.
     * @param excludedEdge An edge to exlude from the calculation.
     * @return Path
     */
    public Path getPath(Node startNode, Node endNode, Edge excludedEdge) {

        Path retPath = directPaths.get(startNode).get(endNode);

        // If direct path is not known.
        if (retPath == null) {
            retPath = new Path(startNode);

            if (startNode != endNode) {
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
                    Node lastNode = currentPath.getLastNode();

                    // Look at each edge of the current node.
                    for (Edge edge : lastNode.getEdges()) {

                        // If not excluded edge.
                        if (edge != excludedEdge) {
                            Node nextNode = edge.getEnd();

                            // If nextNode is not the last node.
                            if (nextNode != currentPath.getLastEdge().getStart()) {

                                // If my current nodes distance is shorter than stored distance.
                                int distanceToNode = currentPath.getCost() + edge.getWeight();
                                if (distanceToNode < minPaths.get(nextNode).getCost() || minPaths.get(nextNode).getCost() == 0) {

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
