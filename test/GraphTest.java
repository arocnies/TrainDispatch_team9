import graph.GraphFactory;
import graph.Graph;
import graph.Node;

/**
 * Created by Aaron on 3/27/2015.
 */


public class GraphTest {


    public static void main(String[] args) {
        GraphFactory gf = new GraphFactory(48, "test.graph");
        Graph myGraph = gf.generateGraph();

        System.out.println(myGraph);

        Node startNode = myGraph.getNodes()[0];
        Node endNode = myGraph.getNodes()[1];
        System.out.println(myGraph.getPath(startNode, endNode));


//        myGraph.getNodes();
//
//        loop through nodes...
//        myGraph.getAdjacency(node);
    }

}
