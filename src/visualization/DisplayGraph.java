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

    private final Map<Node, Integer> nodeSizes;
    private final Map<Edge, Integer> edgeSizes;
    private final Map<Edge, Integer> delaySizes;

    // For picking random colors.
    private String[] colors = {"purple", "aquamarine", "cyan", "magenta"};
    Random rng = new Random();

    private org.graphstream.graph.Graph gsGraph = new SingleGraph(this.toString(), true, true);

    public DisplayGraph(Graph graph) {
        nodeSizes= new HashMap<>(graph.getNodes().size());
        edgeSizes = new HashMap<>(graph.getEdges().size());
        delaySizes = new HashMap<>(graph.getEdges().size());
        graph.getNodes().forEach(n -> nodeSizes.put(n, 10));
        graph.getEdges().forEach(n -> edgeSizes.put(n, 10));
        graph.getEdges().forEach(n -> delaySizes.put(n, 10));
        fillGraph(graph);

        graph.getNodes().stream().filter(n -> n != null).forEach(n -> paint(n, "grey"));
        graph.getEdges().forEach(e -> paint(e, "grey"));
    }

    public void display() {
        // Configure style.
        gsGraph.addAttribute("ui.quality");
        gsGraph.addAttribute("ui.antialias");
        gsGraph.addAttribute("ui.stylesheet", "node { " + Style.node() + "}");
        gsGraph.getNodeSet().forEach(n -> n.addAttribute("ui.label", n.getId()));
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        gsGraph.display();
    }

    public void paint(Path p, boolean isColored) {

        String color = "yellow";
        if (isColored) {
            color = colors[rng.nextInt(colors.length)];
        }
        final String finalColor = color;

        p.getNodeSet().stream().filter(n -> n != null).forEach(n -> paint(n, finalColor));
        p.getEdges().forEach(e -> paint(e, finalColor));
    }

    public void paint(Node n, String color) {
        final int size = nodeSizes.get(n) + 1;
        nodeSizes.put(n, size);
        gsGraph.addAttribute("ui.stylesheet", "node#" + n.toString() + " { " + Style.node(color, size) + " }");
        gsGraph.getNode(n.toString()).addAttribute("layout.weight", size);
    }

    public void paint(Edge e, String color) {
        if (e.getStart() != null) {
            final int size = edgeSizes.get(e) + 1;
            gsGraph.addAttribute("ui.stylesheet", "edge#\"" + e.getSharedId() + "\" { " + Style.edge(color, size / 2) + " }");
            gsGraph.getEdge(e.getSharedId()).addAttribute("layout.weight", e.getWeight() / 2);
        }
    }

    public void paint(Delay d) {
        Edge e = d.getEdge();
        int size = delaySizes.get(e);
        size += d.getCost();
        delaySizes.put(e, size);
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
                    "size: 10px; ";

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
