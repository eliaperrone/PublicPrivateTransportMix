package blablacar;

public class distance {
    private double value;
    private String unity;

    @Override
    public String toString() {
        return "" +
                "value=" + value +
                ", unity='" + unity + '\'' +
                '}';
    }

    public double getValue() {
        return value;
    }
}
