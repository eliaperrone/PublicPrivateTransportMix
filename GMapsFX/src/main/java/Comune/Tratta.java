package Comune;

import Comune.Tappa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tratta {
    private Tappa partenza;
    private Tappa destinazione;
    private LocalDate giornoPartenza;
    private LocalDate giornoArrivo;
    private LocalTime oraPartenza;
    private LocalTime oraArrivo;
    private String mezzo;
    private double distanza;

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Tratta(Tappa partenza, Tappa destinazione, LocalDate gP, LocalTime oP, LocalDate gA, LocalTime oA, String mezzo, double distanza){
        this.partenza=partenza;
        this.destinazione=destinazione;
        this.giornoArrivo=gA;
        this.giornoPartenza=gP;
        this.oraArrivo=oA;
        this.oraPartenza=oP;
        this.mezzo=mezzo;
        this.distanza=distanza;
    }

    public void setMezzo(String mezzo) {
        this.mezzo = mezzo;
    }

    public double getDistanza() {
        return distanza;
    }

    public Tappa getPartenza() {
        return partenza;
    }

    public Tappa getDestinazione() {
        return destinazione;
    }

    public LocalDate getGiornoPartenza() {
        return giornoPartenza;
    }

    public LocalDate getGiornoArrivo() {
        return giornoArrivo;
    }

    public LocalTime getOraPartenza() {
        return oraPartenza;
    }

    public LocalTime getOraArrivo() {
        return oraArrivo;
    }

    public String getMezzo() {
        return mezzo;
    }

    public boolean isWithAuto(){
        return mezzo.equals("Auto");
    }

    public boolean isWithFeet(){
        return mezzo.equals("WALKING");
    }

    @Override
    public String toString() {
        return "Tratta --> " +
                "partenza da=" + partenza.getNome() + "latlong: " + partenza.getPos() +" alle "+oraPartenza.toString()+" del "+giornoPartenza.toString()+
                ",\n\t\t arrivo a=" + destinazione.getNome()+ " latlon: " + destinazione.getPos()+" alle "+oraArrivo.toString()+" del "+giornoArrivo.toString()+ " " +
                "\n\t\tmezzo= "+mezzo+"\n\t\tdistanza="+distanza;
    }

    public String forLeftList(){
        return "Departure from: "+partenza.getNome()+"\tlatitude: "+partenza.getLatitudine()+"\tlongitude: "+partenza.getLongitudine()+"\n\t"+giornoPartenza.toString()+" "+oraPartenza.toString()+
                "\nArrival to: "+destinazione.getNome()+"\tlatitude: "+destinazione.getLatitudine()+"\tlongitude: "+destinazione.getLongitudine()+"\n\t"+giornoArrivo.toString()+" "+oraArrivo+
                "\nWith: "+mezzo+"\nDistance: "+distanza+" km";
    }

    public String forLeftListMini(){
        return "From Latitude: "+partenza.getLatitudine()+"\n\tLongitude: "+partenza.getLongitudine()+"\n\tDate: "+giornoPartenza.toString()+" Hour:"+oraPartenza.toString()+
                "\nTo Latitude: "+destinazione.getLatitudine()+"\n\tLongitude: "+destinazione.getLongitudine()+"\n\tDate: "+giornoArrivo.toString()+" Hour:"+oraArrivo.toString();
    }

    public LocalDateTime getTotPartenza() {
        return LocalDateTime.of(giornoPartenza,oraPartenza);
    }

    public LocalDateTime getTotArrivo() {
        return LocalDateTime.of(giornoArrivo,oraArrivo);
    }

}
