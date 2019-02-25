package com.lynden.gmapsfx.googleTrips;

import java.util.List;

public class ContainerTrip {
    private List<routes> routes;

    public List<com.lynden.gmapsfx.googleTrips.routes> getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "ContainerTrip{" +
                "routes=" + routes +
                '}';
    }
}
