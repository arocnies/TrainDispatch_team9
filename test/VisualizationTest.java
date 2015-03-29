import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import graph.GraphFactory;

public class VisualizationTest {
	public static void main (String args []) {
        Graph graph = new SingleGraph("TestInput");

        graph.Graph myGraph = GraphFactory.generateGraph("test.graph");
        System.out.println(myGraph);

        graph.Node[] nodes = myGraph.getNodes();

        // Loop through nodes.
        for (graph.Node node : nodes) {
            graph.addNode(node.toString());
        }

        // Loop through nodes.
        for (graph.Node node : nodes) {
            graph.Edge[] edges = node.getEdges();

            // Loop through edges.
            for (graph.Edge edge : edges) {
                try {
                    graph.addEdge(edge.toString(), edge.getStart().toString(), edge.getEnd().toString());
                } catch (EdgeRejectedException e) {
                    // Ignore.
                }
            }
        }

		// Displays the graph
		graph.display();

		// Displays labels for all nodes.
		for(Node n : graph) {
			n.addAttribute("ui.label", n.getId());
		}
	}
}
