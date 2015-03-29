package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Aaron on 3/27/2015.
 */


public class GraphFactory {

    /**
     * Returns a Graph object generated from a file.
     * @param fileName The name of the source file.
     * @return Graph generated from file.
     */
    public static Graph generateGraph(String fileName) {

        // Declare graph to return.
        Graph graph = null;

        // Try to load graph from file.
        try {
            // Load file.
            Scanner fileScanner = new Scanner(new File(fileName));
            int nodeCount = fileScanner.nextInt();
            fileScanner.nextLine();

            // Fill nodes with new Nodes.
            Node[] nodes = new Node[nodeCount];
            for (int i = 0; i < nodes.length; i++) {
                nodes[i] = new Node();
            }

            // Loop through nodes.
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {

                String[] edges = fileScanner.nextLine().split("/");
                Edge[] adjacency = new Edge[edges.length];

                // Loop through edges for a node.
                for (int edgeIndex = 0; edgeIndex < edges.length; edgeIndex++) {
                    // Get edges as string array.
                    String[] es = edges[edgeIndex].trim().split(" ");

                    // Parse strings into edges.
                    int weight = Integer.parseInt(es[0]);
                    int destinationIndex = Integer.parseInt(es[1]);
                    Edge edge = new Edge(nodes[nodeIndex], nodes[destinationIndex], weight);

                    // Add edge to adjacency for node.
                    adjacency[edgeIndex] = edge;
                }
                // Set node's adjacency.
                nodes[nodeIndex].setEdges(adjacency);
            }
            graph = new Graph(nodes);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
