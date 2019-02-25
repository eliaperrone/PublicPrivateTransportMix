package com.lynden.gmapsfx.duration;

public class elements {
    private distance distance;
    private duration duration;

    private String status;

    @Override
    public String toString() {
        return "elements{\n\t" +
                "distance=" + distance +
                ", duration=" + duration +
                ", status='" + status + '\'' +
                '}';
    }
}
