package blablacar;

public class arrival_place {
    public String city_name;
    public String address;
    public float latitude;
    public float longitude;
    public String country_code;

    @Override
    public String toString() {
        return  " " +
                "city_name='" + city_name + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country_code='" + country_code + '\'' +
                '}';
    }

    public String getCity_name() {
        return city_name;
    }

    public String getAddress() {
        return address;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry_code() {
        return country_code;
    }
}
