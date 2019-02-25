package blablacar;

public class commission {
    private double value;
    private String currency;
    private String symbol;
    private String string_value;

    @Override
    public String toString() {
        return " " +
                "value=" + value +
                ", currency='" + currency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", string_value='" + string_value + '\'' +
                '}';
    }
}




