import java.util.Objects;

public class Edge {
    private final Node destination;
    private final String trip_id;
    private final int departure_time; // represented in seconds
    private final int arrival_time; // also represented in seconds

    public Edge(Node destination, String trip_id, String departure_time_string, String arrival_time_string) {
        this.destination = Objects.requireNonNull(destination);
        this.trip_id = Objects.requireNonNull(trip_id);

        // converting string value of departure time and arrival time to seconds
        departure_time = timeToSeconds(departure_time_string);
        arrival_time = timeToSeconds(arrival_time_string);

    }

    public int getWeight(int current_time_in_seconds) {
        if (current_time_in_seconds > departure_time) {
            return Integer.MAX_VALUE; // you've missed departure
        }

        return arrival_time - current_time_in_seconds;
    }

    public Node getDestination() {
        return destination;
    }

    private int timeToSeconds(String time) {
        int ret = 0;
        String[] times = time.split(":");
         ret +=
                (Integer.parseInt(times[0]) * 3600) +
                        (Integer.parseInt(times[1]) * 60) +
                        (Integer.parseInt(times[2]));
         return ret;
    }
}
