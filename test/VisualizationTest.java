import graph.Edge;
import graph.GraphFactory;
import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class VisualizationTest {

	public static void main (String args []) {
        Graph graph = new SingleGraph("TestInput");
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // This line does not produce the expected result... why? (See 'http://graphstream-project.org/doc/Tutorials/GraphStream-CSS-Reference_1.2/')
        //graph.addAttribute("ui.stylesheet", "node { text-alignment: under; text-style: bold; text-color: white; text-background-mode: rounded-box; text-background-color: black; text-padding: 1px; text-offset: 0px, 2px; } ");

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
            //n.addAttribute("ui.stylesheet", "node { text-alignment: under; } ");

            //n.addAttribute("ui.stylesheet", "node { text-style: bold; } ");
            //n.addAttribute("ui.stylesheet", "node { text-color: white; } ");
            //n.addAttribute("ui.stylesheet", "node { text-background-mode: rounded-box; } ");
            //n.addAttribute("ui.stylesheet", "node { text-background-color: red; } ");
            //n.addAttribute("ui.stylesheet", "node { text-padding: 1px; } ");
            //n.addAttribute("ui.stylesheet", "node { text-offset: 0px, 2px; } ");
		}

        // Test animation of all paths.
        // This is a messy test of animating paths. Our true visualization should be broken up into proper methods.

        // Loop through all nodes with an inner loop through all nodes.
        for (graph.Node n1 : myGraph.getNodes()) {
            for (graph.Node n2 : myGraph.getNodes()) {

                // Create path between two nodes.
                Path myPath = myGraph.getPath(n1, n2);

                // Color path.
                for (Edge edge : myPath) {
                    graph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId() + "\" { fill-color: red; size: 5px; }");
                    graph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + "\" { fill-color: red; }");
                }
                graph.addAttribute("ui.stylesheet", "node#\"" + n1 + "\" { fill-color: blue; size: 20px; }");
                graph.addAttribute("ui.stylesheet", "node#\"" + n2 + "\" { fill-color: yellow; size: 20px; }");

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
                graph.addAttribute("ui.stylesheet", "node { text-alignment: under; text-style: bold; text-color: white; text-background-mode: rounded-box; text-background-color: black; text-padding: 1px; text-offset: 0px, 2px; } ");

            }
        }
    }
}
