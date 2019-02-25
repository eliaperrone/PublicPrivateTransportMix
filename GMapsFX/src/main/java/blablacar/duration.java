package blablacar;

import java.time.LocalTime;

public class duration {
    private double value;
    private String unity;

    @Override
    public String toString() {
        return " " +
                "value=" + value +
                ", unity='" + unity + '\'' +
                '}';
    }

    public double getValue() {
        return value;
    }

    public String getUnity() {
        return unity;
    }
}
