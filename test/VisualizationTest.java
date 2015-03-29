import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

import graph.GraphFactory;

public class VisualizationTest {

	public static void main (String args []) {
        Graph graph = new SingleGraph("TestInput");


        // This isn't quite working...
//        graph.addAttribute("ui.stylesheet", "node { text-alignment: under; text-style: bold; text-color: white; text-background-mode: rounded-box; text-background-color: red; text-padding: 1px; text-offset: 0px, 2px; } ");

        graph.Graph myGraph = GraphFactory.generateGraph("res/test.graph");
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
                    graph.addEdge(edge.getSharedId(), edge.getStart().toString(), edge.getEnd().toString(), false);
                } catch (EdgeRejectedException | IdAlreadyInUseException e) {
                    // Ignore.
                }
            }
        }

		// Displays the graph
		graph.display();

		// Displays labels for all nodes.
		for (Node n : graph) {
			n.addAttribute("ui.label", n.getId());
		}

        // Create path between two nodes.
        graph.Node startNode = myGraph.getNode("G");
        graph.Node endNode = myGraph.getNode("D");
        Path myPath = myGraph.getPath(startNode, endNode);

        // Color path.
        for (graph.Edge edge : myPath) {
            graph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId() + "\" { arrow-shape: arrow; fill-color: red; size: 5px; }");
            graph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + "\" { fill-color: red; }");
        }
        graph.addAttribute("ui.stylesheet", "node#\"" + startNode + "\" { fill-color: blue; size: 20px; }");
        graph.addAttribute("ui.stylesheet", "node#\"" + endNode + "\" { fill-color: blue; size: 20px; }");

        // Print path to console.
        System.out.println(myPath);
    }
}
