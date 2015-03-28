package visualization;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import factory.GraphFactory;

public class TestInput {
	public static void main (String args []) {
		Graph graph = new SingleGraph("TestInput");
	    GraphFactory gf = new GraphFactory(7, "test.graph");
		
		for ()
	    
	    
		// Adds nodes to the graph
		graph.addNode("Node-1");
		graph.addNode("Node-2");
		graph.addNode("Node-3");
		
		
		// Defines the edges for nodes
		graph.addEdge("Edge-1/2", "Node-1", "Node-2");
		graph.addEdge("Edge-2/3", "Node-2", "Node-3");
		graph.addEdge("Edge-3/1", "Node-3", "Node-1");

		// Displays the graph
		graph.display();
		
		// Prints out a list of all the nodes on the console
		for(Node n:graph.getEachNode()) {
			System.out.println(n.getId());
		}
		
		System.out.println("~~~~~~~~~~~~");
		
		// Prints out a list of all the edges on the console
		for(Edge e:graph.getEachEdge()) {
			System.out.println(e.getId());
		}
	}
}
