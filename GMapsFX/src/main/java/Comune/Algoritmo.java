package Comune;

import blablacar.ContainerAPI;
import com.lynden.gmapsfx.MainApp;
import com.lynden.gmapsfx.duration.GoogleConnection;
import com.lynden.gmapsfx.googleTrips.ContainerTrip;

import java.time.*;

import static blablacar.Connection.createContainerTripBlaBla;
import static blablacar.Connection.createPath;
import static com.lynden.gmapsfx.duration.GoogleConnection.createContainerTrip2;
import static com.lynden.gmapsfx.duration.GoogleConnection.returnSeconds;

public class Algoritmo{

    /**
    Metodo che ricalcola le tratte google a partire dalla tratta che è stata sostituita dall'algoritmo
     */
    static Tratta searchWithGoogle(double latorigin, double longorigin, double latdestination, double longdestination,
                                   String data, LocalTime localTime, boolean isWalking){
        ContainerTrip container;
        String df;
        if(!isWalking){
            container= createContainerTrip2(//&mode=walking
                    GoogleConnection.createPathCoordinates(
                            latorigin,
                            longorigin,
                            latdestination,
                            longdestination,
                            data,
                            localTime.getHour(),
                            localTime.getMinute()
                    )
            );
            df="NOT WALKING";
        }else{//&mode=walking
            container = createContainerTrip2(
                    GoogleConnection.createPathCoordinatesWalking(
                            latorigin,
                            longorigin,
                            latdestination,
                            longdestination,
                            data,
                            localTime.getHour(),
                            localTime.getMinute()
                    )
            );
            df="WALKING";
        }

        System.out.println(latorigin+" "+
                longorigin+" "+
                latdestination+" "+
                longdestination+" "+
                data+" "+
                localTime.getHour()+" "+
                localTime.getMinute()+ " "+ df);

        Viaggio t2;
        t2 = MainApp.fillTratteGoogleMixed(container, false,GoogleConnection.returnDate(data),localTime);

        if(t2.getTratte().size()>1){

            StringBuilder sb2=new StringBuilder();
            System.out.println("ERRORE, ho trovato più di una tratta!");
            double distanza=0;

            for(Tratta t:t2.getTratte()){
                distanza+=t.getDistanza();
                sb2.append(t.getMezzo()+"\n\t");
            }

            return new Tratta(t2.getTratte().get(0).getPartenza(),t2.getTratte().getLast().getDestinazione(),t2.getTratte().get(0).getGiornoPartenza(),t2.getTratte().get(0).getOraPartenza(),t2.getTratte().getLast().getGiornoArrivo(),t2.getTratte().getLast().getOraArrivo(),sb2.toString(),distanza);
        }
        return t2.getTratte().get(0);
    }



/****************************************************** TIME BASED ****************************************************/
    public static Viaggio timeBased(Viaggio riferimento) {

        Viaggio viaggioSoloGoogle = new Viaggio();
        Viaggio viaggioConSostituzione = new Viaggio();
        Viaggio viaggioMigliore = new Viaggio();

        /*
        Dopo aver effettuato la prima ricerca da nodo A a nodo B, vengono copiate tutte le tratte trovate all'interno
        di 2 viaggi temporanei. Il primo viene tenuto come riferimento, mentre il secondo serve per la sostituzione nel
        caso in cui vengano trovate delle tratte con BlaBlaCar.
         */
        for(Tratta t: riferimento.getTratte()) {
            viaggioSoloGoogle.aggiungiTratta(t);
            viaggioConSostituzione.aggiungiTratta(t);
            viaggioMigliore.aggiungiTratta(t);
        }

        System.out.println("############################################################################");

        //Container per le tratte trovate con BlaBlaCar
        ContainerAPI containertemp = null;

        //coordinate delle tappe
        float latitudineTempPartenza = 0;
        float longitudineTempPartenza = 0;
        float latitudineTempDestinazione = 0;
        float longitudineTempDestinazione = 0;

        int index=0; //indice per sapere in quale posizione sostituire la tratta di Gogole con quelloa di BlaBlaCar

        System.out.println("GDPR"); //Fondamentale per il progetto
        System.out.println("\n\n\n");

        int m=0;
        for(Tratta t:viaggioSoloGoogle.getTratte()){
            System.out.println("#####################" + t + "###################" + m);
            m++;

        }
        //Questo for loop scorre tutte le tratte trovate con Google e le sostituisce(se esistono) con quelle di BlaBla
        for (Tratta t : viaggioSoloGoogle.getTratte()) {


            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            latitudineTempPartenza = t.getPartenza().getLatitudine();
            longitudineTempPartenza = t.getPartenza().getLongitudine();

            latitudineTempDestinazione = t.getDestinazione().getLatitudine();
            longitudineTempDestinazione = t.getDestinazione().getLongitudine();

            System.out.println(""
                    + "PARTENZA: LAT= " + latitudineTempPartenza
                    + "    LONG= " + longitudineTempPartenza
                    + "data partenza: " + t.getGiornoPartenza());

            System.out.println(""
                    + "ARRIVO: LAT= " + latitudineTempDestinazione
                    + "    LONG= " + longitudineTempDestinazione
                    + "data arrivo: " + t.getGiornoArrivo());

            //le tratte BlaBlaCar vengono cercate solo se t non è una tratta a piedi
            if(!t.isWithFeet()){
                containertemp = createContainerTripBlaBla(
                        createPath(
                                latitudineTempPartenza,
                                longitudineTempPartenza,
                                latitudineTempDestinazione,
                                longitudineTempDestinazione,
                                t.getGiornoPartenza().toString(),
                                t.getOraPartenza().getHour(),
                                1,
                                "EUR"
                        )
                );
            }else{
                containertemp=null;
            }

            //Se ho trovato la tratta con BlaBlaCar la inserisco nel viaggioConSostituzione
            if (containertemp != null && !containertemp.getTrips().isEmpty()) {
                /*
                prendo il luogo di partenza e di arrivo del viaggio che parte più presto trovato con BlaBlaCar,
                il costruttore che prende i tipi departure_place e arrival_place ricava automaticamente le coordinate
                delle 2 tappe.
                */
                Tappa tappaPartenza = new Tappa(containertemp.getTrips().get(0).getDeparture_place());
                Tappa tappaArrivo = new Tappa(containertemp.getTrips().get(0).getArrival_place());

                System.out.println("giorno partenza: "
                        + containertemp.getTrips().get(0).getDate(containertemp.getTrips().get(0).getDeparture_date()));
                System.out.println("ora partenza: "
                        + containertemp.getTrips().get(0).getTime(containertemp.getTrips().get(0).getDeparture_date()));
                System.out.println("giorno arrivo: "
                        +containertemp.getTrips().get(0).getGArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                        (long) containertemp.getTrips().get(0).getDuration().getValue()));
                System.out.println("ora arrivo: "
                        +containertemp.getTrips().get(0).getOArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                        (long) containertemp.getTrips().get(0).getDuration().getValue()));
                System.out.println("durata: " + containertemp.getTrips().get(0).getDistance().getValue());

                //Costruisco la tratta che rappresenta la tratta calcolata con BlaBlaCar
                Tratta temp = new Tratta(
                        tappaPartenza,
                        tappaArrivo,
                        containertemp.getTrips().get(0).getDate(containertemp.getTrips().get(0).getDeparture_date()),
                        containertemp.getTrips().get(0).getTime(containertemp.getTrips().get(0).getDeparture_date()),
                        containertemp.getTrips().get(0).getGArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                                (long) containertemp.getTrips().get(0).getDuration().getValue()),
                        containertemp.getTrips().get(0).getOArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                                (long) containertemp.getTrips().get(0).getDuration().getValue()),
                        "Auto",
                        containertemp.getTrips().get(0).getDistance().getValue()
                );

                System.out.println("TRATTA BLABLACAR ############## "+temp+ "############ TRATTA BLABLACAR");

                //sostituisco la tratta t con quella corrispondente trovata da BlaBlaCar
                viaggioConSostituzione.replaceTratta(index, temp);

                if (isBetterInDuration(viaggioConSostituzione, viaggioMigliore)){
                    viaggioMigliore.replaceAllTratte(viaggioConSostituzione);
                }

                viaggioConSostituzione.replaceAllTratte(viaggioMigliore);
}
            index++;
        }
        return viaggioMigliore;
    }

/****************************************************** ECO BASED *****************************************************/
    public static Viaggio ecoBased(Viaggio riferimento) {

        Viaggio viaggioSoloGoogle = new Viaggio();//viaggio solo google
        Viaggio viaggioConSostituzione = new Viaggio();//viaggio che utilizziamo come appoggio
        Viaggio viaggioMigliore = new Viaggio();//viaggio finale

        /*
        Dopo aver effettuato la prima ricerca da nodo A a nodo B, vengono copiate tutte le tratte trovate all'interno
        di 2 viaggi temporanei. Il primo viene tenuto come riferimento, mentre il secondo serve per la sostituzione nel
        caso in cui vengano trovate delle tratte con BlaBlaCar.
         */
        for(Tratta t: riferimento.getTratte()) {
            viaggioSoloGoogle.aggiungiTratta(t);
            viaggioConSostituzione.aggiungiTratta(t);
            viaggioMigliore.aggiungiTratta(t);
        }

        System.out.println("############################################################################");

        //Container per le tratte trovate con BlaBlaCar
        ContainerAPI containertemp = null;

        //coordinate delle tappe
        float latitudineTempPartenza = 0;
        float longitudineTempPartenza = 0;
        float latitudineTempDestinazione = 0;
        float longitudineTempDestinazione = 0;

        int index=0; //indice per sapere in quale posizione sostituire la tratta di Gogole con quelloa di BlaBlaCar

        System.out.println("GDPR"); //Fondamentale per il progetto
        System.out.println("\n\n\n");

        //Questo for loop scorre tutte le tratte trovate con Google e le sostituisce(se esistono) con quelle di BlaBla
        for (Tratta t : viaggioMigliore.getTratte()) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            latitudineTempPartenza = t.getPartenza().getLatitudine();
            longitudineTempPartenza = t.getPartenza().getLongitudine();

            latitudineTempDestinazione = t.getDestinazione().getLatitudine();
            longitudineTempDestinazione = t.getDestinazione().getLongitudine();

            //le tratte BlaBlaCar vengono cercate solo se t non è una tratta a piedi
            if(!t.isWithFeet()){
                containertemp = createContainerTripBlaBla(
                        createPath(
                                latitudineTempPartenza,
                                longitudineTempPartenza,
                                latitudineTempDestinazione,
                                longitudineTempDestinazione,
                                t.getGiornoPartenza().toString(),
                                t.getOraPartenza().getHour(),
                                1,
                                "EUR"
                        )
                );
            }else{
                containertemp=null;
            }

            //Se ho trovato la tratta con BlaBlaCar la inserisco nel viaggioConSostituzione
            if (containertemp != null && !containertemp.getTrips().isEmpty()) {
                /*
                prendo il luogo di partenza e di arrivo del viaggio che parte più presto trovato con BlaBlaCar,
                il costruttore che prende i tipi departure_place e arrival_place ricava automaticamente le coordinate
                delle 2 tappe.
                */
                Tappa tappaPartenza = new Tappa(containertemp.getTrips().get(0).getDeparture_place());
                Tappa tappaArrivo = new Tappa(containertemp.getTrips().get(0).getArrival_place());

                Tratta temp = new Tratta(
                        tappaPartenza,
                        tappaArrivo,
                        containertemp.getTrips().get(0).getDate(containertemp.getTrips().get(0).getDeparture_date()),
                        containertemp.getTrips().get(0).getTime(containertemp.getTrips().get(0).getDeparture_date()),
                        containertemp.getTrips().get(0).getGArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                                (long) containertemp.getTrips().get(0).getDuration().getValue()),
                        containertemp.getTrips().get(0).getOArrivo(containertemp.getTrips().get(0).getDeparture_date(),
                                (long) containertemp.getTrips().get(0).getDuration().getValue()),
                        "Auto",
                        containertemp.getTrips().get(0).getDistance().getValue()
                );

                System.out.println("TRATTA BLABLACAR ############## "+temp+ "############ TRATTA BLABLACAR");

                //sostituisco la tratta t con quella corrispondente trovata da BlaBlaCar
                viaggioConSostituzione.replaceTratta(index, temp);

                if (isBetterInEco(viaggioConSostituzione, viaggioMigliore)){
                    viaggioMigliore.replaceAllTratte(viaggioConSostituzione);
                }

                viaggioConSostituzione.replaceAllTratte(viaggioMigliore);
            }
            index++;
        }
        return viaggioMigliore;
    }

/*************************************************** CHECK METHODES ***************************************************/
    static boolean isBetterInDuration(Viaggio viaggio1, Viaggio viaggio2){
        System.out.println("\n\n\nSto confrontando "+viaggio1+" con\n"+viaggio2);
        if (viaggio1==null || viaggio2==null){
            System.out.println("TRAVEL NOT FOUND");
            return false;
        }

        try {
            System.out.println(viaggio1.getDurata() + " , " + viaggio2.getDurata());
        } catch (Exception e){
            e.printStackTrace();
        }

        if(viaggio2.getDurata()<viaggio1.getDurata()) {
            System.out.println("BLABLACAR is not the best choice in terms of trip duration!");
            return false;
        }else {
            return true;
        }
    }

    static boolean isBetterInEco(Viaggio viaggio1, Viaggio viaggio2){
        if (viaggio1==null || viaggio2==null){
            System.out.println("TRAVEL NOT FOUND");
            return false;
        }
        System.out.println("BLABLACAR ECO");
        return true;
    }
}

