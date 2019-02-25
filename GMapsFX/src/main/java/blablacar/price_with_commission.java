package blablacar;

public class price_with_commission {
    private double value;
    private String currency;
    private String symbol;
    private String string_value;
    private String price_color;

    @Override
    public String toString() {
        return " " +
                "value=" + value +
                ", currency='" + currency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", string_value='" + string_value + '\'' +
                ", price_color='" + price_color + '\'' +
                '}';
    }
}
