package com.lynden.gmapsfx.googleTrips;

public class transit_details {
    private arrival_time arrival_time;
    private departure_time departure_time;
    private line line;

    @Override
    public String toString() {
        return "transit_details{" +
                "arrival_time=" + arrival_time +
                ", departure_time=" + departure_time + ", line="+line+
                '}';
    }

    public com.lynden.gmapsfx.googleTrips.arrival_time getArrival_time() {
        return arrival_time;
    }

    public com.lynden.gmapsfx.googleTrips.departure_time getDeparture_time() {
        return departure_time;
    }

    public com.lynden.gmapsfx.googleTrips.line getLine() {
        return line;
    }
}
