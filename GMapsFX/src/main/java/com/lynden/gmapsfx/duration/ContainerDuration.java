package com.lynden.gmapsfx.duration;

import java.util.List;

public class ContainerDuration {
    private List<String> destination_addresses;
    private List<String> origin_addresses;

    private List<rows> rows;

    private String status;

    @Override
    public String toString() {
        return "ContainerDuration{\n\t" +
                "destination_addresses='" + destination_addresses + '\'' +
                ", origin_addresses='" + origin_addresses + '\'' +
                ", rows=" + rows +
                ", status='" + status + '\'' +
                '}';
    }
}
