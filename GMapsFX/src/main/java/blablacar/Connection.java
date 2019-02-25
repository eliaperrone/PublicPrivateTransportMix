package blablacar;

import com.google.gson.Gson;
import com.lynden.gmapsfx.MainApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.DoubleBuffer;

public class Connection {

    public static void main(String[] args) {
        ContainerAPI container = createContainerTripBlaBla(
                createPath(
                        (float)46.1473,
                        (float)9.302,
                        (float)45.479699,
                        (float)9.129108,
                        "2018-05-17",
                        11,
                        1,
                        "EUR"
                )
        );
        System.out.println(container);
    }

    public static ContainerAPI createContainerTripBlaBla(String path) {
        ContainerAPI container = null;
        try {
            URL url = new URL (path);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            Gson gson = new Gson();
            container = gson.fromJson(reader, ContainerAPI.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return container;
    }

    public static String createPath(float latitude, float longitude,
                                    float latitude2, float longitude2, String date, int hour, int seats, String currency) {


        String s= "https://public-api.blablacar.com/api/v2/trips?key=51dc911c86824837963de36f5ed96ae6" +
                "&fc=" + Float.toString(latitude) + "%7C" + Float.toString(longitude) +
                "&tc=" + Float.toString(latitude2) + "%7C" + Float.toString(longitude2) +
                "&db=" + date +
                "&hb=" + hour +
                "&seats=" + seats +
                "&cur=" + currency +
                "&radius_from=" + MainApp.radius.getText()+
                "&radius_to=" +MainApp.radius.getText()+
                "&limit=1&_format=json";
        System.out.println(s);
        return s;
    }

    public static String createPathWithoutCoordinates(String from, String to, String date, int hour, int seats, String currency) {


        return "https://public-api.blablacar.com/api/v2/trips?key=51dc911c86824837963de36f5ed96ae6" +
                "&fn=" + from +
                "&tn=" + to +
                "&db=" + date +
                "&hb=" + hour +
                "&seats=" + seats +
                "&cur=" + currency +

                "&radius_from=" + MainApp.radius.getText()+
                "&radius_to=" + MainApp.radius.getText()+

                "&limit=1&_format=json";
    }

}

