import graph.Edge;
import graph.GraphFactory;
import graph.Path;
import org.graphstream.graph.*;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import visualization.Styles;

import java.util.Set;

public class VisualizationTest {

    private static Graph gsGraph;
    private static graph.Graph graph;
    private static Styles style;

	public static void main (String args []) {

        // Create graphs using provided args.
        graph = GraphFactory.generateGraph(args[0]);
        gsGraph = loadGsGraph(graph);
        style = new Styles();

		// Improve the output and initial labeling
        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");
        gsGraph.addAttribute("ui.stylesheet", style.standardNode());
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // Displays the graph
        gsGraph.display();

		// Displays labels for all nodes.
        gsGraph.getNodeSet().forEach(n -> n.addAttribute("ui.label", n.getId()));

        // Display paths with one second interval.
        displayAllPaths(1000);
    }

    // --------------- Other methods ---------------

    public static void displayAllPaths(int interval) {
        // Loop through all nodes with an inner loop through all nodes.
        for (graph.Node n1 : graph.getNodes()) {
            for (graph.Node n2 : graph.getNodes()) {

                // Create path between two nodes.
                Path myPath = graph.getPath(n1, n2);

                // Color path.
                for (Edge edge : myPath) {
                    gsGraph.addAttribute("ui.stylesheet", "edge#\"" + edge.getSharedId() + style.redEdge() );
                    gsGraph.addAttribute("ui.stylesheet", "node#\"" + edge.getEnd() + style.redNode() );
                }
                gsGraph.addAttribute("ui.stylesheet", "node#\"" + n1 + style.startNode());
                gsGraph.addAttribute("ui.stylesheet", "node#\"" + n2 + style.endNode());

                // Print path to console.
                System.out.println(myPath);

                // Sleep the thread one second.
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Remove colors from graph.
                gsGraph.removeAttribute("ui.stylesheet");
                gsGraph.addAttribute("ui.stylesheet", style.standardNode());
            }
        }
    }

    public static Graph loadGsGraph(graph.Graph myGraph) {
        Graph graph = new SingleGraph("TestInput");
        System.out.println(myGraph);

        Set<graph.Node> nodes = myGraph.getNodes();

        // Add nodes to GraphStream graph.
        for (graph.Node node : nodes) {
            graph.addNode(node.toString());
        }

        // Add edges to GraphStream graph.
        for (graph.Node node : nodes) {
            Set<graph.Edge> edges = node.getEdges();

            // Loop through edges.
            for (graph.Edge edge : edges) {
                try {
                    // Must use getSharedId for single edged graphs.
                    graph.addEdge(edge.getSharedId(), edge.getStart().toString(), edge.getEnd().toString(), false);
                } catch (EdgeRejectedException | IdAlreadyInUseException e) {
                    System.out.println("Ignoring duplicate edge:" + edge);
                }
            }
        }
        return graph;
    }
}
