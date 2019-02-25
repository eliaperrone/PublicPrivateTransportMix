package com.lynden.gmapsfx.googleTrips;

import java.util.List;

public class routes {
    private List<legs> legs;

    public List<com.lynden.gmapsfx.googleTrips.legs> getLegs() {
        return legs;
    }

    @Override
    public String toString() {
        return "routes{" +
                "legs=" + legs +
                '}';
    }
}
