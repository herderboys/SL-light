import java.io.*;
import java.util.Objects;

public class GraphLoader {

    private double maxSpeed;

    public GraphLoader(Graph graph, File stopsFile, File stopTimesFile) {
        maxSpeed = 0;
        load(graph, stopsFile, stopTimesFile);
    }

    public void load(Graph graph, File stopsFile, File stopTimesFile) {
        try {

            // loading the stops into the graph
            BufferedReader stopsReader = new BufferedReader(new FileReader(stopsFile));
            stopsReader.readLine();

            String line;
            String currentStopId;
            String currentStopName;
            String currentStopLat;
            String currentStopLon;

            while ((line = stopsReader.readLine()) != null) {
                String[] split = line.split(",");

                currentStopId = split[0];
                currentStopName = split[1];
                currentStopLat = split[2];
                currentStopLon = split[3];

                graph.add(new Node(currentStopId, currentStopName, Double.parseDouble(currentStopLat), Double.parseDouble(currentStopLon)));
            }
            stopsReader.close();


            // loading the timetable (initializing edges) into the graph
            BufferedReader timesReader = new BufferedReader(new FileReader(stopTimesFile));
            timesReader.readLine();

            String previousTripId = "";
            String previousDepartureTime = "";
            String previousStopId = "";

            String currentTripId;
            String currentArrivalTime;
            String currentDepartureTime;
            // stop id already defined

            while ((line = timesReader.readLine()) != null) {
                String[] split = line.split(",");

                currentTripId = split[0];
                currentArrivalTime = split[1];
                currentDepartureTime = split[2];
                currentStopId = split[3];

                if (Objects.equals(currentTripId, previousTripId)) {
                    graph.connect(currentStopId, previousStopId, currentTripId, previousDepartureTime, currentArrivalTime);

                    /* calculate max speed of an edge for using as base for heuristic in A* algorithm.
                     could separate this to a different class, but this would increase the workload,
                     so might as well do it here */
                    int timeElapsed = Edge.timeToSeconds(currentArrivalTime) - Edge.timeToSeconds(previousDepartureTime);

                    if (timeElapsed > 0) {
                        double dx = graph.getNode(currentStopId).getX() - graph.getNode(previousStopId).getX();
                        double dy = graph.getNode(currentStopId).getY() - graph.getNode(previousStopId).getY();

                        double hypotenuse = Math.sqrt((dx * dx) + (dy * dy));

                        // calculating speed
                        double currentSpeed = hypotenuse / timeElapsed;

                        if (currentSpeed > maxSpeed) {
                            maxSpeed = currentSpeed;
                        }
                    }
                }
                previousTripId = currentTripId;
                previousDepartureTime = currentDepartureTime;
                previousStopId = currentStopId;
            }
            timesReader.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
