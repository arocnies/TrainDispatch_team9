import graph.GraphFactory;
import graph.Graph;
import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */

public class GraphTest {

    public static void main(String[] args) {
        Graph myGraph = GraphFactory.generateGraph("res/test.graph");

        System.out.println(myGraph);

        Node startNode = myGraph.getNode("G");
        Node endNode = myGraph.getNode("E");
        System.out.println(myGraph.getPath(startNode, endNode));
    }
}
