package Comune;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Viaggio {
    private LinkedList<Tratta> tratte;
    private LocalDateTime partenza;
    private LocalDateTime arrivo;
    private long durata;
    private double distance;

    public Viaggio(){
        tratte=new LinkedList<>();
        distance=0;
    }

    public LinkedList<Tratta> getTratte() {
        return tratte;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void aggiungiTratta(Tratta t){
        tratte.add(t);
    }

    @Override
    public String toString() {
        return updateDate().toString();
    }

    public String toString2() {
        return "Viaggio{" +
                "tratte=" + tratte +
                ", partenza=" + partenza +
                ", arrivo=" + arrivo +
                '}';
    }

    public StringBuilder updateDate(){
        StringBuilder sb=new StringBuilder();
        int i=0;

        for(Tratta t:tratte){
            System.out.println("TTTTTTTRatta "+t);
            if(i==0){
                partenza=t.getTotPartenza();
            }
            else{
                arrivo=t.getTotArrivo();
            }
            sb.append(t+"\n");
            i++;
        }

        sb.append("\n###############Partenza: "+partenza+" Arrivo: "+arrivo+" durata: " + getDurata());

        return sb;
    }


    public void replaceAllTratteByIndex(int index){
        for (int i = index; i < tratte.size(); i++) {
            boolean isWalking=false;
            if(tratte.get(i).getMezzo().equals("WALKING"))
                isWalking=true;

            Tratta t = Algoritmo.searchWithGoogle(
                    tratte.get(i).getPartenza().getLatitudine(),
                    tratte.get(i).getPartenza().getLongitudine(),
                    tratte.get(i).getDestinazione().getLatitudine(),
                    tratte.get(i).getDestinazione().getLongitudine(),
                    tratte.get(i-1).getGiornoArrivo().toString(),
                    tratte.get(i-1).getOraArrivo(),isWalking);

            System.out.println("°°°°°°°°°°°°°°°°°°°°°°°°°°°Partenza: " + t.getGiornoPartenza() + " " + t.getOraPartenza() + "    arrivo: " + t.getGiornoArrivo() + " " + t.getOraArrivo());

            tratte.set(i, t);
        }
    }

    public void replaceAllTratte(Viaggio viaggio){
        int index = 0;
        for(Tratta t: viaggio.tratte) {
            tratte.set(index, t);
            index++;
        }
    }

    public void replaceTratta(int index, Tratta t){

        tratte.set(index, t);

        if(index!=(tratte.size()-1)) {
            //Ricalcolo tutte le tratte che vengono dopo quella inserita
            replaceAllTratteByIndex(index+1);
        }
    }

    /*
    Aggiorna la durata in base all'arrivo e alla partenza
     */
    public long getDurata() {
        LocalDateTime p=LocalDateTime.of(tratte.getFirst().getGiornoPartenza(),tratte.getFirst().getOraPartenza());
        LocalDateTime a=LocalDateTime.of(tratte.getLast().getGiornoArrivo(),tratte.getLast().getOraArrivo());
        return p.until(a, ChronoUnit.MINUTES);
    }

    public double getDistance(){
        for(Tratta t:tratte){
            distance+=t.getDistanza();
        }
        return distance;
    }
}
