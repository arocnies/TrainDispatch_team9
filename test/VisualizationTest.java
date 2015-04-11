import graph.Edge;
import graph.GraphFactory;
import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import visualization.Styles;

public class VisualizationTest {

	public static void main (String args []) {
        Graph graph = new SingleGraph("TestInput");
        Styles style = new Styles();
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph.Graph myGraph = GraphFactory.generateGraph("res/weight_test.graph");
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
                    // Can change getSharedId to toString for multidirectional
                    graph.addEdge(edge.toString(), edge.getStart().toString(), edge.getEnd().toString(), false);
                } catch (EdgeRejectedException | IdAlreadyInUseException e) {
                    System.out.println("Ignoring duplicate edge:" + edge);
                }
            }
        }

		// Improve the output and initial labeling
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.addAttribute("ui.stylesheet", style.standardNode());

        // Displays the graph
		graph.display();

		// Displays labels for all nodes.
		for (Node n : graph) {
			n.addAttribute("ui.label", n.getId());
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
                    graph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId()+ style.redEdge() );
                     //"\"  { shadow-mode: plain; shadow-color: red; shadow-offset: 0; }");
                    graph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + style.redNode() );
                           //"\" { shadow-mode: plain; shadow-color: red; shadow-offset: 0; }");
                }
                graph.addAttribute("ui.stylesheet", "node#\"" + n1 + style.startNode());
                graph.addAttribute("ui.stylesheet", "node#\"" + n2 + style.endNode());

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
                graph.addAttribute("ui.stylesheet", style.standardNode());
                //graph.addAttribute("ui.stylesheet", "node { text-alignment: under; text-style: bold; text-color: white; text-background-mode: rounded-box; text-background-color: black; text-padding: 1px; text-offset: 0px, 2px; } ");

            }
        }
    }
}
