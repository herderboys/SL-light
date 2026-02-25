import java.io.*;

public class GraphLoader {
    public GraphLoader(Graph graph, File stopsFile, File stopTimesFile) {
        load(graph, stopsFile, stopTimesFile);
    }

    public void load(Graph graph, File stopsFile, File stopTimesFile) {
        try {
            BufferedReader stopsReader = new BufferedReader(new FileReader(stopsFile));
            stopsReader.readLine();

            String line;
            String stop_id;
            String stop_name;
            String stop_lat;
            String stop_lon;

            while ((line = stopsReader.readLine()) != null) {
                String[] split = line.split(",");

                stop_id = split[0];
                stop_name = split[1];
                stop_lat = split[2];
                stop_lon = split[3];

                graph.add(new Node(stop_id, stop_name, Double.parseDouble(stop_lat), Double.parseDouble(stop_lon)));
            }
            stopsReader.close();


            BufferedReader timesReader = new BufferedReader(new FileReader(stopTimesFile));

            timesReader.readLine();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
