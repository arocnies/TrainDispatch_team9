import graph.Edge;
import graph.GraphFactory;
import graph.Graph;
import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */

public class GraphTest {

    public static void main(String[] args) {
        Graph myGraph = GraphFactory.generateGraph(args[0]);

        System.out.println(myGraph);

        // Check if graph is undirected.
        boolean graphUndirected = true;

        // Loop through nodes.
        for (Node node: myGraph.getNodes()) {

            // Loop through node's edges.
            for (Edge edge : node.getEdges()) {
                boolean edgeUndirected = false;

                // Find match for edge.
                for (Edge e2 : edge.getEnd().getEdges()) {
                    if (e2.getEnd() == node && edge.getWeight() == e2.getWeight()) {
                        edgeUndirected = true;
                        break;
                    }
                }

                // If match not found, flag as directed.
                if (!edgeUndirected) {
                    graphUndirected = false;
                    System.out.println("Edge " + edge + " has no complement.");
                }
            }
        }
        System.out.println(graphUndirected? "Graph is undirected!" : "Graph is directed!");
    }
}
