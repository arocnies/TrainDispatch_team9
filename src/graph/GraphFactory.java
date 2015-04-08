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
            List<Node> nodes = new ArrayList<>(nodeCount);
            for (int i = 0; i < nodeCount; i++) {
                nodes.add(new Node());
            }

            // Loop through nodes.
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {

                String[] edges = fileScanner.nextLine().split("/");
                Set<Edge> adjacency = new HashSet<>(edges.length);

                // Loop through edges for a node.
                for (String edge1 : edges) {
                    // Get edges as string array.
                    String[] es = edge1.trim().split(" ");

                    // Parse strings into edges.
                    int weight = Integer.parseInt(es[0]);
                    int destinationIndex = Integer.parseInt(es[1]);
                    Edge edge = new Edge(nodes.get(nodeIndex), nodes.get(destinationIndex), weight);

                    // Add edge to adjacency for node.
                    adjacency.add(edge);
                }
                // Set node's adjacency.
                nodes.get(nodeIndex).setEdges(adjacency);
            }
            graph = new Graph(new HashSet<>(nodes));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return graph;
    }
}
