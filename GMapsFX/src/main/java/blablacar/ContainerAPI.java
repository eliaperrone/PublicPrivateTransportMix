package blablacar;



import java.util.List;

public class ContainerAPI{
    private links links;
    private transient pager pager;
    private List<BlaBlaTrips> trips;

    private List<TopTrips> top_trips;
    private List<Facets> facets;

    private int distance;
    private int duration;
    private int reccomended_price;
    private int savings;
    private int lowest_price;
    private int full_trips_count;
    private String tracktor;
    private String sorting_algorithm;


    @Override
    public String toString() {
        return "ContainerAPI{" + trips +
                '}';
    }

    public List<BlaBlaTrips> getTrips() {
        return trips;
    }
}

