import graph.*;
import graph.Edge;
import graph.GraphFactory;
import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.Random;

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

        // Test animation of all paths.
        // This is a messy test of animating paths. Our true visualization should be broken up into proper methods.
        String[] letters = {"A", "B", "C", "D", "E", "F", "G"};

        // Loop through all letters with a nested loop through all letters.
        for (String n1 : letters) {
            for (String n2 : letters) {

                // Create path between two nodes.
                graph.Node startNode = myGraph.getNode(n1);
                graph.Node endNode = myGraph.getNode(n2);
                Path myPath = myGraph.getPath(startNode, endNode);

                // Color path.
                for (Edge edge : myPath) {
                    graph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId() + "\" { fill-color: red; size: 5px; }");
                    graph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + "\" { fill-color: red; }");
                }
                graph.addAttribute("ui.stylesheet", "node#\"" + startNode + "\" { fill-color: blue; size: 20px; }");
                graph.addAttribute("ui.stylesheet", "node#\"" + endNode + "\" { fill-color: yellow; size: 20px; }");

                // Print path to console.
                System.out.println(myPath);

                // Sleep the thread one second.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Remove colors from graph.
                graph.removeAttribute("ui.stylesheet");
            }
        }
    }
}
