package com.lynden.gmapsfx.googleTrips;

import java.util.Date;

public class arrival_time {
    private String text;
    private String time_zone;
    private long value;

    @Override
    public String toString() {
        System.out.println("---------------------------------------arrival_time{" +
                "text='" + text + '\'' +
                ", time_zone='" + time_zone + '\'' +
                ", value=" + value +
                '}');
        return "arrival_time{" +
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
