package graph;

/**
 * Created by Aaron on 3/27/2015.
 */

public class Node {
    private final String name;

    public Node() {
        name = nextName();
    }

    @Override
    public String toString() {
        return name;
    }


    private static int nodeInstanceCount;
    private static String nextName() {

        StringBuilder name = new StringBuilder();
        int n = nodeInstanceCount + 65;

        while (n > 155) {
            name.append(Character.toChars(n % 65));
            n -= 65;
        }

        name.append(Character.toChars(n));
        nodeInstanceCount++;
        return name.toString();
    }
}
