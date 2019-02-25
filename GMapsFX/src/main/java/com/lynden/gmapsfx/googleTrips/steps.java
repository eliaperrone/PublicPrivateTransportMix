package com.lynden.gmapsfx.googleTrips;

import Comune.Algoritmo;
import Comune.Tappa;
import Comune.Tratta;
import com.lynden.gmapsfx.MainApp;

import java.time.*;
import java.util.List;

public class steps {
    private transit_details transit_details;
    private distance distance;
    private duration duration;
    private start_location start_location;
    private end_location end_location;
    private List<steps> steps;
    private String travel_mode;

    public com.lynden.gmapsfx.googleTrips.transit_details getTransit_details() {
        return transit_details;
    }

    public com.lynden.gmapsfx.googleTrips.distance getDistance() {
        return distance;
    }

    public com.lynden.gmapsfx.googleTrips.duration getDuration() {
        return duration;
    }

    public com.lynden.gmapsfx.googleTrips.start_location getStart_location() {
        return start_location;
    }

    public com.lynden.gmapsfx.googleTrips.end_location getEnd_location() {
        return end_location;
    }

    public List<com.lynden.gmapsfx.googleTrips.steps> getSteps() {
        return steps;
    }

    public String getTravel_mode() {
        return travel_mode;
    }

    @Override
    public String toString() {
        return "steps{" +
                "transit_details=" + transit_details +
                ", distance=" + distance +
                ", duration=" + duration +
                ", start_location=" + start_location +
                ", end_location=" + end_location +
                ", steps=" + steps +
                ", travel_mode='" + travel_mode + '\'' +
                '}';
    }
}
