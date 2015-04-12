package visualization;

import dispatch.Delay;
import graph.Edge;
import graph.Graph;
import graph.Node;
import graph.Path;
import org.graphstream.graph.IdAlreadyInUseException;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by Aaron on 4/12/2015.
 */

public class DisplayGraph {

    private final Map<Edge, Integer> edgeSizes;

    // For picking random colors.
    private String[] colors = {"purple", "aquamarine", "cyan", "magenta"};
    Random rng = new Random();

    private org.graphstream.graph.Graph gsGraph = new SingleGraph(this.toString(), true, true);

    public DisplayGraph(Graph graph) {
        edgeSizes = new HashMap<>(graph.getNodes().size());
        graph.getEdges().forEach(n -> edgeSizes.put(n, 10));
        fillGraph(graph);
    }

    public void display() {
        // Configure style.
        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");
        gsGraph.addAttribute("ui.stylesheet", "node { " + Style.node() + "}");
        gsGraph.getNodeSet().forEach(n -> n.addAttribute("ui.label", n.getId()));
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        gsGraph.display();
    }

    public void paint(Path p, boolean isColored) {

        String color = "yellow";
        if (isColored) {
            color = colors[rng.nextInt(colors.length)];
        }
        final String finalColor = color;

        p.getNodeSet().stream().filter(n -> n != null).forEach(n ->
            gsGraph.addAttribute("ui.stylesheet", "node#" + n.toString() + " { " + Style.node(finalColor) + " }"));
        p.getEdges().forEach(
                e -> gsGraph.addAttribute("ui.stylesheet", "edge#\"" + e.getSharedId() + "\" { " + Style.edge(finalColor) + " }"));
    }

    public void paint(Delay d) {
        Edge e = d.getEdge();
        int size = edgeSizes.get(e);
        size += d.getCost();
        edgeSizes.put(e, size);
        gsGraph.addAttribute("ui.stylesheet", "edge#\"" + e.getSharedId() + "\" { " + Style.edge("red", size / 2) + " }");
    }

    private void fillGraph(Graph graph) {
        Set<Node> nodes = graph.getNodes();
        nodes.forEach(n -> gsGraph.addNode(n.toString()));
        for (Edge edge : graph.getEdges()) {
            try {
                gsGraph.addEdge(edge.getSharedId(), edge.getStart().toString(), edge.getEnd().toString());
            }
            catch (IdAlreadyInUseException e) {
                System.out.println("Ignoring duplicate edge.");
            }
        }
    }
}

class Style {

    public static final String node =
                    "text-alignment: under; " +
                    "fill-color: #778899; " + //#778899 is grey
                    "text-style: bold; " +
                    "text-color: white; " +
                    "text-background-mode: rounded-box; " +
                    "text-background-color: #000000; " +
                    "text-padding: 2; " +
                    "text-offset: 10, 2; " +
                    "stroke-mode: plain; " +
                    "size: 15px; ";

    private static final String edge =
                    "fill-color: black; " +
                    "size: 5px; ";

    private static final String specialNode =
            node + "fill-color: green; size: 20px; ";

    public static String node() {
        return node;
    }

    public static String edge() {
        return edge;
    }

    public static String node(String color) {
        String retString;

        switch (color) {
            case "start":
                retString = specialNode + "fill-color: green; ";
                break;
            case "end":
                retString = specialNode + "fill-color: orange; ";
                break;
            default:
                retString = node + "fill-color: " + color + "; ";
                break;
        }

        return retString;
    }

    public static String edge(String color) {
        return edge + "fill-color: " + color + "; ";
    }

    public static String node(String color, int size) {
        return node(color) + "size: " + size + "px; ";
    }

    public static String edge(String color, int size) {
        return edge(color) + "size: " + size + "px; ";
    }
}
