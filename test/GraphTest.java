import graph.GraphFactory;
import graph.Graph;
import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */


public class GraphTest {


    public static void main(String[] args) {
        Graph myGraph = GraphFactory.generateGraph("test.graph");

        System.out.println(myGraph);

        Node startNode = myGraph.getNodes()[0];
        Node endNode = myGraph.getNodes()[1];
        System.out.println(myGraph.getPath(startNode, endNode));
    }

}
