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


        public String standardNode() {

             //  The standard node: BLACK background - WHITE text - BLACK node

            return style = "node { " +
                    "text-alignment: under; " +
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

        public String startNode() {

                //  The starting node, filled with a vibrant green and larger

                return style = "\" {" +
                        "fill-color: green;" +
                        "size: 20px; }";
        }


        public String endNode() {

                // The end node, filled with a darker green and larger

                return style = "\" {" +
                        "fill-color: #006400;" +
                        "size: 20px; }";
        }



        public String boldRedEdge() {

            // A BOLD RED on the edge

            return style = //"edge { " +
                    "{ " +
                    "fill-color: red; " +
                    "size: 5px; }";
        }



        public String redNode() {

                // A RED outlined node

                return style = //"node { " +
                        "\" {" +
                        "shadow-mode: plain;" +
                        "shadow-color: red;" +
                        "shadow-offset: 0;" +
                        "shadow-width: 2; " +
                        "}";
        }



        public String redEdge() {

            // A RED outlined edge

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: red;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String orangeNode() {

            // An ORANGE outlined node

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: orange;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String orangeEdge() {

            // An ORANGE outlined edge

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: orange;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String yellowNode() {

            // A YELLOW outlined node

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: yellow;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String yellowEdge() {

            // A YELLOW outlined edge

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: yellow;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String blueNode() {

            // A BLUE outlined node

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: blue;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String blueEdge() {

            // A BLUE outlined edge

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: blue;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String greenNode() {

            // A GREEN outlined node

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: green;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
        }



        public String greenEdge() {

            // A GREEN outlined edge

            return style = //"edge { " +
                    "\" {" +
                    "shadow-mode: plain;" +
                    "shadow-color: green;" +
                    "shadow-offset: 0;" +
                    "shadow-width: 2; }";
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
