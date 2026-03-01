public class RouteState implements Comparable<RouteState> {
    private final Node currentNode;
    private final int arrivalTime;
    private final double score;
    private final RouteState prev;
    private final Edge incomingEdge;

    public RouteState(Node startingNode, int arrivalTime,
                      double score, RouteState prev, Edge incomingEdge) {
        this.currentNode = startingNode;
        this.arrivalTime = arrivalTime;
        this.score = score;
        this.prev = prev;
        this.incomingEdge = incomingEdge;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public double getScore() {
        return score;
    }

    public RouteState getPrev() {
        return prev;
    }

    public Edge getIncomingEdge() {
        return incomingEdge;
    }

    @Override
    public int compareTo(RouteState other) {
        return Double.compare(score, other.getScore());
    }
}
