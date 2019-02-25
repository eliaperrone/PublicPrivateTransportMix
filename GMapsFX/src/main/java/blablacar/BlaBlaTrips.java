package blablacar;

import Comune.Tappa;
import Comune.Tratta;
import com.lynden.gmapsfx.MainApp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class BlaBlaTrips {
    private links links;

    private String departure_date;

    private departure_place departure_place;
    private arrival_place arrival_place;
    private price price;
    private blablacar.price_with_commission price_with_commission;
    private price_without_commission price_without_commission;
    private commission commission;

    private int seats;

    private duration duration;
    private distance distance;

    private List<String> locations_to_display;

    public String getDeparture_date() {
        return departure_date;
    }

    public blablacar.duration getDuration() {
        return duration;
    }

    public blablacar.distance getDistance() {
        return distance;
    }

    public LocalDate getDate(String dat){
        return LocalDate.of(Integer.parseInt(dat.substring(6,10)),Integer.parseInt(dat.substring(3,5)),Integer.parseInt(dat.substring(0,2)));
    }
    public LocalTime getTime(String dat){
        return LocalTime.of(Integer.parseInt(dat.substring(11,13)),Integer.parseInt(dat.substring(14,16)));
    }
    public LocalDate getGPartenza(String dat){
        LocalDate dg=getDate(dat);
        return dg;
    }

    public LocalTime getOPartenza(String dat){
        LocalTime og=getTime(dat);
        return og;
    }


    //La data e l'ora di arrivo sono calcolate sommando la durata del viaggio alla data/orario di partenza
    public LocalDate getGArrivo(String dat, long durata){
        LocalDate dg=getDate(dat);
        LocalTime og=getTime(dat);
        LocalDateTime ldt=LocalDateTime.of(dg,og);
        ldt=ldt.plusSeconds(durata);
        //System.out.println("\n\n\n\n\n\n\n\n\n"+LocalDate.of(ldt.getYear(),ldt.getMonth().getValue(),ldt.getDayOfMonth()));
        return LocalDate.of(ldt.getYear(),ldt.getMonth().getValue(),ldt.getDayOfMonth());
    }

    public LocalTime getOArrivo(String dat, long durata){
        LocalDate dg=getDate(dat);
        LocalTime og=getTime(dat);
        LocalDateTime ldt=LocalDateTime.of(dg,og);
        ldt=ldt.plusSeconds(durata);
        //System.out.println("\n\n\n\n\n\n\n\n\n"+LocalTime.of(ldt.getHour(),ldt.getMinute()));
        return LocalTime.of(ldt.getHour(),ldt.getMinute());
    }

    @Override
    public String toString() {

        /*MainApp.tappeBlaBla.add(new Tappa(departure_place));
        MainApp.tappeBlaBla.add(new Tappa(arrival_place));
        Tratta t=new Tratta(new Tappa(departure_place),new Tappa(arrival_place),getDate(departure_date),getTime(departure_date),getGArrivo(departure_date,(long)duration.getValue()),getOArrivo(departure_date,(long)duration.getValue()),"Auto",distance.getValue());
        MainApp.tratteBlaBla.add(t);
        MainApp.viaggio.aggiungiTratta(t);*/

        return "\n--->Passaggi{ " +
                ", \n\tData partenza='" + departure_date + '\'' +
                ", \n\tLuogo partenza=" + departure_place +
                ", \n\tLuogo Arrivo=" + arrival_place +
                ", \n\tPrezzo=" + price +
                ", \n\tPrezzo con commissioni=" + price_with_commission +
                ", \n\tPrezzo senza commissioni=" + price_without_commission +
                ", \n\tCommissioni=" + commission +
                ", \n\tPosti=" + seats +
                ", \n\tDurata=" + duration +
                ", \n\tDistanza=" + distance +
                ", \n\tTappe=" + locations_to_display +
                '}';
        //return " ";
    }

    public departure_place getDeparture_place() {
        return departure_place;
    }

    public arrival_place getArrival_place() {
        return arrival_place;
    }
}
