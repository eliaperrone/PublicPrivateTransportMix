package com.lynden.gmapsfx.duration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lynden.gmapsfx.googleTrips.ContainerTrip;
//import data_storage.ContainerAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

// https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=Washington,DC&destinations=New+York+City,NY&key=AIzaSyD4zonOqGtWr7Uye0HEdTFRN0DjUjVbLuw

public class GoogleConnection {

    public static void main(String[] args) {
        ContainerTrip container = createContainerTrip2(
                createPath(
                        "Milano",
                        "Roma",
                        "2018-04-20"
                )
        );

        System.out.println(container);
        //System.out.println(returnSeconds(s, 15, 30));

    }


    public static ContainerDuration createContainerTrip(String path) {
        ContainerDuration container = null;
        try {
            URL url = new URL (path);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            container = gson.fromJson(reader, ContainerDuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return container;
    }

    public static ContainerTrip createContainerTrip2(String path) {
        ContainerTrip container = null;
        try {
            System.out.println(path);
            URL url = new URL (path);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            container = gson.fromJson(reader, ContainerTrip.class);
            //System.out.println(container.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return container;
    }

    public static String createPath(String from, String to, String date) {


        long  epoch = System.currentTimeMillis()/1000 + 86400;
        return "https://maps.googleapis.com/maps/api/directions/json?origin=Rome&destination=London&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
        //return "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&departure_time=" + epoch + "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
    }

    public static String createPath2(String from, String to, String date, int hour, int min) {
        long epoch=returnSeconds(date,hour,min);
        return "https://maps.googleapis.com/maps/api/directions/json?" +
                "origin="+from+
                "&destination="+to+
                "&departure_time=" + epoch+
                "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
        //return "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&departure_time=" + epoch + "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
    }

    public static String createPathCoordinates(double latorigin, double longorigin, double latdestination, double longdestination, String date, int hour, int min) {
        long epoch=returnSeconds(date,hour,min);
        return "https://maps.googleapis.com/maps/api/directions/json?"+
            "origin="+ latorigin + "," + longorigin +
            "&destination="+ latdestination + "," + longdestination +
            "&departure_time=" + epoch+
            "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
        //return "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&departure_time=" + epoch + "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
    }

    public static String createPathCoordinatesWalking(double latorigin, double longorigin, double latdestination, double longdestination, String date, int hour, int min) {
        long epoch=returnSeconds(date,hour,min);
        return "https://maps.googleapis.com/maps/api/directions/json?"+
                "origin="+ latorigin + "," + longorigin +
                "&destination="+ latdestination + "," + longdestination +
                "&departure_time=" + epoch+
                "&mode=walking&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
        //return "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&origins=" + from + "&destinations=" + to + "&departure_time=" + epoch + "&mode=transit&traffic_model=optimistic&language=it-IT&key=AIzaSyBlU6FHoths_wjlH56gUwuwdRER66Wpydc";
    }


    public static long returnSeconds(String s, int hour, int minute) {//il giorno prima all'una di notte
        int year,month,date;
        year=Integer.parseInt(s.substring(0,4));
        month=Integer.parseInt(s.substring(5,7));
        date=Integer.parseInt(s.substring(8,10));
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(1969, 12, 31,1,0);
        calendar2.set(year, month, date,hour,minute);
        long milliseconds1 = calendar1.getTimeInMillis();
        long milliseconds2 = calendar2.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;
        long seconds = diff / 1000;
        System.out.println("SECONDSsssssssssssssssss: "+seconds);
        return seconds;
    }

    public static LocalDate returnDate(String s){
        int year,month,date;
        year=Integer.parseInt(s.substring(0,4));
        month=Integer.parseInt(s.substring(5,7));
        date=Integer.parseInt(s.substring(8,10));
        return LocalDate.of(year,month,date);
    }
}

