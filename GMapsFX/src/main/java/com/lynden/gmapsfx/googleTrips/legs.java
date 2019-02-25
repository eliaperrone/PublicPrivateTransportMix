package com.lynden.gmapsfx.googleTrips;

import java.util.List;

public class legs {
    private arrival_time arrival_time;
    private departure_time departure_time;
    private distance distance;
    private duration duration;
    private start_location start_location;
    private end_location end_location;
    private String start_address;
    private String end_address;
    private List<steps> steps;

    public List<com.lynden.gmapsfx.googleTrips.steps> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "legs{" +
                "arrival_time=" + arrival_time +
                "\n departure_time=" + departure_time +
                "\n distance=" + distance +
                "\n duration=" + duration +
                "\n start_location=" + start_location +
                "\n end_location=" + end_location +
                "\n start_address='" + start_address + '\'' +
                "\n end_address='" + end_address + '\'' +
                "\n steps=" + steps +
                '}';
    }
}
