package graph;

import graph.Edge;
import graph.Graph;
import graph.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */


public class GraphFactory {

    private Scanner fileScanner;
    private final int nodeAmount;

    public GraphFactory(int nodes, String fileName) {
        nodeAmount = nodes;
        loadFile(fileName);
    }


    /**
     * Creates an adjacency map of Nodes to Lists of their adjacent Nodes.
     * @return Map Nodes to List of Nodes (use this for creating Graphs).
     */
    public Map<Node, List<Edge>> generateAdjacencyMap() {

        // AdjacencyList using a map with Nodes as keys.
        Map<Node, List<Edge>> adjacencyMap = new HashMap<Node, List<Edge>>(nodeAmount);

        // Array for building adjacencyMap.
        Node[] nodes = new Node[nodeAmount];

        // Fill nodes array with empty nodes.
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
        }

        // Loop through nodes.
        for (int nodeIndex = 0; nodeIndex < nodeAmount; nodeIndex++) {
            List<Edge> adjacency = new LinkedList<Edge>();

            String[] edges = fileScanner.nextLine().split("/");

            // Loop through edges for a node.
            for (int j = 0; j < edges.length; j++) {
                String[] es = edges[j].trim().split(" ");
                int weight = Integer.parseInt(es[0]);
                int destIndex = Integer.parseInt(es[1]);
                Edge edge = new Edge(nodes[nodeIndex], nodes[destIndex], weight);
                adjacency.add(edge);
            }

            // Map the node to its adjacency.
            adjacencyMap.put(nodes[nodeIndex], adjacency);
        }

        return adjacencyMap;
    }

    public Graph generateGraph() {
        Graph graph = new Graph(generateAdjacencyMap());
        return graph;
    }


    /**
     * Loads a file into the fileScanner.
     * @param fileName Name and/or path to file
     */
    private void loadFile(String fileName) {
        try {
            fileScanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
