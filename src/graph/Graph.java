package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

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
     * Returns a Path object leading from startNode to endNode.
     * @param startNode The node of origin.
     * @param endNode The final destination node.
     * @return Path
     */
    public Path getPath(Node startNode, Node endNode ) {

        List<Node> nodes = new LinkedList<Node>();

//        path.addNode(startNode);
//
//        while ()


        return null;
    }





    /**
     * Returns a list of Path objects leading from startNode to endNode.
     * @param limit The maximum number of paths to return
     * @param startNode The node of origin.
     * @param endNode The final destination node.
     * @return List of Paths
     */
    public List<Path> getPaths(int limit, Node startNode, Node endNode) {
        // Get edges from starting node.
        // Add edges to priority queue
        // Remove current node from queue.
        // Go to highest priority node.
        // Add

        PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>();
        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node n = priorityQueue.poll();

            // Visit each node.
            for (Edge e : n.getEdges()) {
                int weight = e.getWeight();

            }
        }



        return null;
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
                sb.append(" (" + edge.getWeight() + ", " + edge.getNodeBeta() + ")");
//                sb.append(" (" + edge + ")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
