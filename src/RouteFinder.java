import java.util.*;

public class RouteFinder {
    public RouteFinder() {
    }

    // A* algorithm
    public RouteState findRoute(Graph graph, String startID, String targetID, String departureTime, double maxSpeed) {
        PriorityQueue<RouteState> queue = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();

        Node startingNode = graph.getNode(startID);
        Node targetNode = graph.getNode(targetID);

        int departureTimeSeconds = Edge.timeToSeconds(departureTime);

        double hValue = hValue(startingNode, targetNode, maxSpeed);

        RouteState first = new RouteState(startingNode, departureTimeSeconds, departureTimeSeconds + hValue, null, null);
        queue.add(first);

        while (!queue.isEmpty()) {

            RouteState currentState = queue.poll();
            String currentID = currentState.getCurrentNode().getStop_ID();

            if (Objects.equals(currentID, targetID)) {
                return currentState;
            }
            if (visited.contains(currentID)) {
                continue;
            }
            visited.add(currentID);
            List<Edge> currentEdges = currentState.getCurrentNode().getEdges();

            for (Edge edge : currentEdges) {

                // check so edge that is before current edge can't be used next
                if ((!visited.contains(edge.getDestination().getStop_ID())) && (currentState.getArrivalTime() <= edge.getDepartureTime())) {
                    hValue = hValue(edge.getDestination(), targetNode, maxSpeed);
                    RouteState next = new RouteState(edge.getDestination(), edge.getArrivalTime(), edge.getArrivalTime() + hValue, currentState, edge);
                    queue.add(next);
                }
            }

        }
        return null;
    }

    public void printRoute(RouteState routeState) {
        if (routeState == null) {
            System.out.println("No path found!");
            return;
        }

        Stack<RouteState> path = new Stack<>();
        RouteState temp = routeState;

        while (temp != null && temp.getIncomingEdge() != null) {
            path.push(temp);
            temp = temp.getPrev();
        }

        String currentTripId = null;
        String boardingStop = null;
        String boardingTime = null;

        String arrivalStop = null;
        String arrivalTime = null;

        while (!path.isEmpty()) {
            RouteState step = path.pop();
            Edge e = step.getIncomingEdge();

            if (currentTripId == null) {
                // First step of the journey
                currentTripId = e.getTripId();
                boardingStop = e.getOriginName();
                boardingTime = formatSeconds(e.getDepartureTime());
            } else if (!e.getTripId().equals(currentTripId)) {
                // We are transferring! Print the leg we just finished.
                System.out.println("Board Trip " + currentTripId + " at " + boardingStop +
                        " [" + boardingTime + "] -> Arrive at " +
                        arrivalStop + " [" + arrivalTime + "]");

                // Reset trackers for the new trip
                currentTripId = e.getTripId();
                boardingStop = e.getOriginName();
                boardingTime = formatSeconds(e.getDepartureTime());
            }

            // Always update the arrival trackers so we know where we eventually stop
            arrivalStop = step.getCurrentNode().getName();
            arrivalTime = formatSeconds(e.getArrivalTime());
        }

        // Print the final leg of the journey once the loop finishes
        if (currentTripId != null) {
            System.out.println("Board Trip " + currentTripId + " at " + boardingStop +
                    " [" + boardingTime + "] -> Arrive at " +
                    arrivalStop + " [" + arrivalTime + "]");
        }
    }

    public String formatSeconds(int totalSeconds) {
        int h = totalSeconds / 3600;
        int m = (totalSeconds % 3600) / 60;
        int s = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public double hValue(Node node1, Node node2, double maxSpeed) {
        double dx = node1.getX() - node2.getX();
        double dy = node1.getY() - node2.getY();

        return Math.sqrt((dx * dx) + (dy * dy)) / maxSpeed;
    }


}