import graph.Edge;
import graph.GraphFactory;
import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import visualization.Styles;

import java.util.Set;

public class VisualizationTest {
    public static final Graph gsGraph = new SingleGraph("TestInput");
    public static final Styles style = new Styles();

	public static void main (String args []) {
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph.Graph myGraph = GraphFactory.generateGraph("res/test.graph");
        System.out.println(myGraph);

        Set<graph.Node> nodes = myGraph.getNodes();

        // Loop through nodes.
        for (graph.Node node : nodes) {
            gsGraph.addNode(node.toString());
        }

        // Loop through nodes.
        for (graph.Node node : nodes) {
            Set<graph.Edge> edges = node.getEdges();

            // Loop through edges.
            for (graph.Edge edge : edges) {
                try {
                    gsGraph.addEdge(edge.getSharedId(), edge.getStart().toString(), edge.getEnd().toString(), false);
                } catch (EdgeRejectedException | IdAlreadyInUseException e) {
                    // Ignore.
                }
            }
        }

		// Improve the output and initial labeling
        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");
        gsGraph.addAttribute("ui.stylesheet", style.standardNode());

        // Displays the graph
        gsGraph.display();

		// Displays labels for all nodes.
		for (Node n : gsGraph) {
			n.addAttribute("ui.label", n.getId());
		}

        testPaths(myGraph);
        testPaths(myGraph);
    }

    public static void testPaths(graph.Graph myGraph) {
        // Test animation of all paths.
        // Loop through all nodes with an inner loop through all nodes.
        for (graph.Node n1 : myGraph.getNodes()) {
            for (graph.Node n2 : myGraph.getNodes()) {

                // Create path between two nodes.
                Path myPath = myGraph.getPath(n1, n2);

                // Color path.
                for (Edge edge : myPath) {
                    gsGraph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId() + style.redEdge() );
                    // "\"  { shadow-mode: plain; shadow-color: red; shadow-offset: 0; }");
                    //graph.addAttribute("ui.stylesheet", style.redEdge());
                    gsGraph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + //style.redNode() );
                            "\"  { shadow-mode: plain; shadow-color: red; shadow-offset: 0; }");
                    //graph.addAttribute("ui.stylesheet", style.redNode());
                }
                gsGraph.addAttribute("ui.stylesheet", "node#\"" + n1 + "\" { fill-color: blue; size: 20px; }");
                gsGraph.addAttribute("ui.stylesheet", "node#\"" + n2 + "\" { fill-color: green; size: 20px; }");

                // Print path to console.
                System.out.println(myPath);

                // Sleep the thread one second.
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Remove colors from graph.
                gsGraph.removeAttribute("ui.stylesheet");
                gsGraph.addAttribute("ui.stylesheet", style.standardNode());
                //graph.addAttribute("ui.stylesheet", "node { text-alignment: under; text-style: bold; text-color: white; text-background-mode: rounded-box; text-background-color: black; text-padding: 1px; text-offset: 0px, 2px; } ");

            }
        }
    }
}
