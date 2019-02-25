package com.lynden.gmapsfx.googleTrips;

public class end_location {
    private float lat;
    private float lng;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "end_location{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
