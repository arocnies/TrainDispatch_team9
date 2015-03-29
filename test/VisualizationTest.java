import org.graphstream.graph.Edge;
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
                    graph.addEdge(edge.toString(), edge.getNodeAlpha().toString(), edge.getNodeBeta().toString());
                } catch (EdgeRejectedException e) {
                    // Ignore.
                }
            }
        }

		// Displays the graph
		graph.display();

		// Prints out a list of all the nodes on the console
		for(Node n:graph.getEachNode()) {
			System.out.println(n.getId());
		}

		System.out.println("~~~~~~~~~~~~");

		// Prints out a list of all the edges on the console
		for(Edge e:graph.getEachEdge()) {
			System.out.println(e.getId());
		}
	}
}
