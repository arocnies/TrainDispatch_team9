package graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Aaron on 3/27/2015.
 */


public class Graph {

    private final Map<Node, List<Edge>> adjacencyMap;

    /**
     * Constructor for new graph using the provided adjacencyMap.
     * @param adjMap Map of Nodes to List of Nodes
     */
    public Graph(Map<Node, List<Edge>> adjMap) {
        adjacencyMap = adjMap;
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




        return null;
    }

    public List<Edge> getAdjacency(Node node) {
        return adjacencyMap.get(node);
    }


    public Node[] getNodes() {
        return adjacencyMap.keySet().toArray(new Node[adjacencyMap.keySet().size()]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Loop through all nodes
        for (Node name: adjacencyMap.keySet()){
            List<Edge> list = adjacencyMap.get(name);
            sb.append("[" + name + "]");

            for (Edge n : list) {
                sb.append(" (" + n.getWeight() + ", " + n.getNodeB() + ")");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
