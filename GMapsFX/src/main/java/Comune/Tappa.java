package Comune;

import blablacar.arrival_place;
import blablacar.departure_place;
import com.lynden.gmapsfx.googleTrips.end_location;
import com.lynden.gmapsfx.googleTrips.start_location;

public class Tappa {
    private String nome;
    private String indirizzo;
    private float latitudine;
    private float longitudine;
    private String codicenaz;

    public Tappa(String nome) {
        this.nome = nome;

    }
    public Tappa(departure_place dep){
        this.nome=dep.getCity_name();
        this.indirizzo=dep.getAddress();
        this.latitudine=dep.getLatitude();
        this.longitudine=dep.getLongitude();
        this.codicenaz=dep.getCountry_code();
    }

    public Tappa(arrival_place dep){
        this.nome=dep.getCity_name();
        this.indirizzo=dep.getAddress();
        this.latitudine=dep.getLatitude();
        this.longitudine=dep.getLongitude();
        this.codicenaz=dep.getCountry_code();
    }

    public Tappa(float lat, float lon){
        this.nome="unknown";
        this.indirizzo="unknown";
        this.latitudine=lat;
        this.longitudine=lon;
        this.codicenaz="unknown";

    }

    public Tappa(start_location st){
        this.nome="unknown";
        this.indirizzo="unknown";
        this.latitudine=st.getLat();
        this.longitudine=st.getLng();
        this.codicenaz="unknown";

    }

    public Tappa(end_location st){
        this.nome="unknown";
        this.indirizzo="unknown";
        this.latitudine=st.getLat();
        this.longitudine=st.getLng();
        this.codicenaz="unknown";

    }

    public String getPos(){
        return "Lat: "+this.latitudine+";Long: "+this.longitudine+";";
    }

    public String getNome() {
        return nome;
    }

    public float getLatitudine() {
        return latitudine;
    }

    public float getLongitudine() {
        return longitudine;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getCodicenaz() {
        return codicenaz;
    }

    @Override
    public String toString() {
        return "Tappa: " +
                "nome='" + nome + '\'' +
                ", indirizzo='" + indirizzo + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", codicenaz='" + codicenaz + '\'' +
                '}';
    }
}
