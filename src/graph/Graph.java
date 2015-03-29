package graph;

import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Graph {

    private final Node[] nodes;

    /**
     * Constructor for new graph using the provided nodes.
     * @param nodeArray Array of Nodes
     */
    public Graph(Node[] nodeArray) {
        nodes = nodeArray;
    }

    /**
     * Returns a Path object leading from startNode to endNode found using Dijkstra's algorithm.
     * @param startNode The node of origin.
     * @param endNode The final destination node.
     * @return Path
     */
    public Path getPath(Node startNode, Node endNode ) {

        // Minimum distance from startNode to key node.
        Map<Node, Path> minPaths = new HashMap<>(nodes.length);
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

        return minPaths.get(endNode);
    }

    public Node[] getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Loop through all nodes
        for (Node name: nodes) {
            sb.append("[" + name + "]");

            Edge[] adjacentEdges = name.getEdges();
            for (Edge edge : adjacentEdges) {
                sb.append(" (" + edge.getWeight() + ", " + edge.getEnd() + ")");
//                sb.append(" (" + edge + ")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

//    /**
//     * Returns a list of Path objects leading from startNode to endNode.
//     * @param limit The maximum number of paths to return
//     * @param startNode The node of origin.
//     * @param endNode The final destination node.
//     * @return List of Paths
//     */
//    public List<Path> getPaths(int limit, Node startNode, Node endNode) {
//
//
//
//
//        return null;
//    }
}
