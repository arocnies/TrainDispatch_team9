package visualization;

/**
 * Created by Jennifer on 4/2/2015.
 */


/**
     * Various styles for the nodes and edges of the visualization are included here
     * You will need to include:
     *     System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
     */



public class Styles{

        String style;
        //System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        public String standardNode(){

            /**
             * The standard node: BLACK background - WHITE text - BLACK node
             */

            return style = "node { text-alignment: under; " +
                    "fill-color: #778899; " + //#778899 is grey
                    "text-style: bold; " +
                    "text-color: white; " +
                    "text-background-mode: rounded-box; " +
                    "text-background-color: #000000; " +
                    "text-padding: 2; " +
                    "text-offset: 10, 2; " +
                    "stroke-mode: plain; " +
                    "size: 15px; " +
                    "} ";
        }

        public String boldRedEdge(){
            return style = "edge { fill-color: red; " +
                    "size: 5px; }";
        }

/*
// Trying to make it even more compact
        public void testStyle(Graph x) {
            style = "node { text-alignment: under; " +
                    "fill-color: black; " +
                    "text-style: bold; " +
                    "text-color: white; " +
                    "text-background-mode: rounded-box; " +
                    "text-background-color: #000000; " + //#778899 is grey
                    "text-padding: 2; " +
                    "text-offset: 10, 2; } ";
            x.addAttribute("ui.stylesheet", style);
        }
*/
}
