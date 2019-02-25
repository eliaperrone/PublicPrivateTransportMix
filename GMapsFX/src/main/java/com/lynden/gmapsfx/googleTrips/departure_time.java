package com.lynden.gmapsfx.googleTrips;

public class departure_time {
    private String text;
    private String time_zone;
    private long value;

    @Override
    public String toString() {
        return "departure_time{" +
                "text='" + text + '\'' +
                ", time_zone='" + time_zone + '\'' +
                ", value=" + value +
                '}';
    }

    public String getText() {
        return text;
    }

    public String getTime_zone() {
        return time_zone;
    }

    public long getValue() {
        return value;
    }
}
