package com.lynden.gmapsfx.googleTrips;

import java.util.List;

public class line {
    private List<agencies> agencies;
    private String name; //nome della linea
    private vehicle vehicle;

    @Override
    public String toString() {
        return agencies +" " + name + " " + vehicle;
    }
}

