import java.io.File;

public class SLlight {
    // testing program
    public static void main(String[] args) {
        Graph graph = new Graph();
        File stopsFile = new File("resources/sl_stops.txt");
        File stopTimesFile = new File("resources/sl_stop_times.txt");

        GraphLoader graphLoader = new GraphLoader(graph, stopsFile, stopTimesFile);
        RouteFinder routeFinder = new RouteFinder();

        routeFinder.printRoute(routeFinder.findRoute(graph, "740000716", "740011060", "08:00:00", graphLoader.getMaxSpeed()));


    }
}