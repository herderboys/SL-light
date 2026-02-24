import java.util.ArrayList;
import java.util.List;

public class Node {
    private String stop_id;
    private String name;
    private double x;
    private double y;
    private ArrayList<Edge> edges;

    public Node(String stop_id, String name, double x, double y) {
        this.stop_id = stop_id;
        this.name = name;
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    public String getStop_ID() {
        return stop_id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        if (edge != null && !edges.contains(edge)) {
            edges.add(edge);
        }
    }
}
