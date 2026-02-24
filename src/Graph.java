import java.util.*;

public class Graph {

    private Map<String, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void add(Node node) {
        if (node != null) {
            nodes.putIfAbsent(node.getStop_ID(), node);
        }
    }

    // connect method requires nodes to already be in nodes list
    public void connect(String to, String from, String trip_id,
                        String departure_time_string, String arrival_time_string) {

        Node toNode = nodes.get(to);
        Node fromNode = nodes.get(from);

        if (toNode == null || fromNode == null) {
            System.err.println("Warning: Skipping invalid edge...");
            return;
        }

        Edge edge = new Edge(toNode, trip_id, departure_time_string, arrival_time_string);
        fromNode.addEdge(edge);
    }


    public boolean nodeExists(Node node) {
        return node != null;
    }
}