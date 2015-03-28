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

        while (n > 90) {
            name.append(Character.toChars((n / 90) + 64));
            n -= 26;
        }

        name.append(Character.toChars(n));
        nodeInstanceCount++;
        return name.toString();
    }
}
