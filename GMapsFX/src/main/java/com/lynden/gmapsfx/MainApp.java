package com.lynden.gmapsfx;

import Comune.Algoritmo;
import Comune.Viaggio;
import blablacar.BlaBlaTrips;
import blablacar.ContainerAPI;
import Comune.Tappa;
import Comune.Tratta;
import com.google.maps.model.LatLng;
import com.lynden.gmapsfx.duration.GoogleConnection;
import com.lynden.gmapsfx.googleTrips.ContainerTrip;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.elevation.ElevationResult;
import com.lynden.gmapsfx.service.elevation.ElevationServiceCallback;
import com.lynden.gmapsfx.service.elevation.ElevationStatus;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingServiceCallback;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;

import geocodingrequest.Address;
import geocodingrequest.NominatimReverseGeocodingJAPI;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import netscape.javascript.JSObject;

import javax.swing.*;

import static blablacar.Connection.createContainerTripBlaBla;
import static blablacar.Connection.createPathWithoutCoordinates;
import static com.lynden.gmapsfx.duration.GoogleConnection.createContainerTrip2;
import static com.lynden.gmapsfx.duration.GoogleConnection.createPath2;
import static javafx.application.Application.launch;

/**
 * Example Application for creating and loading a GoogleMap into a JavaFX
 * application
 *
 * @author Rob Terpilowski
 */
public class MainApp extends Application implements MapComponentInitializedListener,
        ElevationServiceCallback, GeocodingServiceCallback, DirectionsServiceCallback{

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    //protected DirectionsPane directions;

    //private Button btnZoomIn;
    //private Button btnZoomOut;
    private Label lblZoom;
    private Label lblCenter;
    private Label lblClick;
    private ComboBox<MapTypeIdEnum> mapTypeCombo;



    private Label lbldist;
    private Label lblduration;

    private Label lblRadius;

    private MarkerOptions markerOptions2;
    private Marker myMarker2;
    private Button btnHideMarker;
    private Button btnDeleteMarker;

    public static TextField startLocation;/////////////////////
    public static TextField finalLocation;

    public static TextField radius;

    private Button searchButton;
    private Button switchButton;
    private Button refreshAll;//
    private ComboBox mode;
    private ComboBox time;//
    public static DatePicker calendar;//
    private Button setOptions;//


    public static JList<String> listaTratte;
    public static ComboBox colour;

    private final String pattern = "yyyy-MM-dd";

    public static LinkedHashSet<Tappa> tappeBlaBla=new LinkedHashSet<>();
    public static LinkedHashSet<Tratta> tratteGoogle=new LinkedHashSet<>();
    public static LinkedHashSet<Tratta> tratteBlaBla=new LinkedHashSet<>();
    public static Viaggio viaggio=new Viaggio();

    public static ContainerTrip container;

    public int scelta=0;

    public static LocalTime lc;

    public static String[] data;

    public static VBox box;



    ListView<Tratta> list = new ListView<Tratta>();
    ObservableList<Tratta> data2 = FXCollections.observableArrayList();
    final Label label = new Label();

    @Override
    public void start(final Stage stage) throws Exception {
        System.out.println("Java version: " + System.getProperty("java.home"));
        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInitializedListener(this);

        BorderPane bp = new BorderPane();
        HBox tb = new HBox();
        HBox tb2 = new HBox();//
        box=new VBox();
        box.setVisible(true);
        box.setMaxWidth(400);



        lblZoom = new Label();
        lblCenter = new Label();
        lblClick = new Label();

        lblRadius=new Label();
        lblRadius.setText("Radius (km): ");
        lblRadius.setVisible(false);

        calendar=new DatePicker();
        calendar.setMaxWidth(120);
        calendar.setMinWidth(100);
        calendar.setValue(LocalDate.now());

        lbldist=new Label("Distance: ");
        lblduration=new Label("Duration: ");

        Image playI=new Image("file:/Users/eliaperrone/Desktop/Viaggi_ottimizzati_versione_finale/ProgettoCarsharing/arrows.png");
        ImageView iv1=new ImageView(playI);
        iv1.setFitHeight(10);
        iv1.setFitWidth(10);
        switchButton=new Button("",iv1);

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Eco Based");
        rb1.setToggleGroup(group);
        rb1.setSelected(true);

        RadioButton rb2 = new RadioButton("Time Based");
        rb2.setToggleGroup(group);

        radius=new TextField();
        radius.setPromptText("2");
        radius.setText("2");
        radius.setVisible(false);
        radius.setMaxWidth(50);
        //radius.addEventFilter(KeyEvent.KEY_TYPED,n);

        rb1.setVisible(false);
        rb2.setVisible(false);

        mapTypeCombo = new ComboBox<>();
        mapTypeCombo.setOnAction( e -> {
            map.setMapType(mapTypeCombo.getSelectionModel().getSelectedItem() );
        });
        mapTypeCombo.setDisable(true);

        Button btnType = new Button("Map type");
        btnType.setOnAction(e -> {
            map.setMapType(MapTypeIdEnum.HYBRID);
        });

        startLocation=new TextField();///////////////////////////
        finalLocation=new TextField();
        startLocation.setPromptText("Type here");
        finalLocation.setPromptText("Type here");

        calendar.setPromptText("Date");
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        box = new VBox();
        box.setMinWidth(300);
        //box.setMaxWidth(300);
        box.getChildren().addAll(list, label);
        VBox.setVgrow(list, Priority.ALWAYS);
        label.setLayoutX(10);
        label.setLayoutY(115);
        label.setFont(Font.font("Verdana", 20));

        list.setItems(data2);

        list.setCellFactory(new Callback<ListView<Tratta>, ListCell<Tratta>>() {
            @Override
            public ListCell<Tratta> call(ListView<Tratta> list) {
                return new ColorRectCell();
            }
        });

        list.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Tratta>() {
                    public void changed(ObservableValue<? extends Tratta> ov,
                                        Tratta old_val, Tratta new_val) {
                        label.setText(new_val.forLeftList());
                        final double MAX_FONT_SIZE = 14.0; // define max font size you need
                        label.setFont(new Font(MAX_FONT_SIZE)); // set to Label
                    }
                });


        calendar.setConverter(converter);
        calendar.setEditable(false);
        calendar.setVisible(false);
        calendar.setShowWeekNumbers(false);///////////////////
        calendar.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });

        lblduration.setVisible(false);
        lbldist.setVisible(false);
        Image play2=new Image("file:/Users/eliaperrone/Desktop/Viaggi_ottimizzati_versione_finale/ProgettoCarsharing/search.png");
        ImageView iv2=new ImageView(play2);
        iv2.setFitHeight(20);
        iv2.setFitWidth(20);
        searchButton=new Button("",iv2);
        searchButton.setId("allbtn");

        Image play3 = new Image("file:/Users/eliaperrone/Desktop/Viaggi_ottimizzati_versione_finale/ProgettoCarsharing/refresh.png");
        ImageView iv3 = new ImageView(play3);
        iv3.setFitHeight(20);
        iv3.setFitWidth(20);
        refreshAll = new Button("", iv3);
        refreshAll.setId("refbtn");

        Image play4 = new Image("file:/Users/eliaperrone/Desktop/Viaggi_ottimizzati_versione_finale/ProgettoCarsharing/filter.png");
        ImageView iv4 = new ImageView(play4);
        iv4.setFitHeight(20);
        iv4.setFitWidth(20);
        setOptions=new Button("",iv4);
        setOptions.setId("fltbtn");

        Image play5 = new Image("file:/Users/eliaperrone/Desktop/Viaggi_ottimizzati_versione_finale/ProgettoCarsharing/list.png");
        ImageView iv5 = new ImageView(play5);
        iv5.setFitHeight(20);
        iv5.setFitWidth(20);

        ObservableList<String> timeOptions = FXCollections.observableArrayList(
                "00:00","00:30","01:00","01:30","02:00","02:30","03:00","03.30","04:00",
                "04:30","05:00","05:30","06:00","06:30","07:00","07:30","08:00","08:30","09:00",
                "09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00",
                "14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00",
                "19:30","20:00","20:30","21:00","21:30","22:00","22:30","23.00","23:30"
        );


        time = new ComboBox(timeOptions);
        time.setVisible(false);
        time.setEditable(false);
        time.setMinWidth(90);
        time.setMaxWidth(90);

        AtomicReference<Calendar> localtime = new AtomicReference<>(Calendar.getInstance());
        AtomicReference<SimpleDateFormat> sdf = new AtomicReference<>(new SimpleDateFormat("HH:mm"));
        time.setValue(sdf.get().format(localtime.get().getTime()));


        ObservableList<String> options =
                FXCollections.observableArrayList("Travel Mode",
                        "Only BlaBlaCar",
                        "Only Public Transport",
                        "Mixed mode"
                );
        mode = new ComboBox(options);
        mode.getSelectionModel().selectFirst();
        mode.setOnAction(e->{

            if(mode.getValue().toString().equals("Mixed mode")){
                radius.setVisible(true);
                lblRadius.setVisible(true);
                rb1.setVisible(true);
                rb2.setVisible(true);
                tb.setBackground(new Background(new BackgroundFill(Color.rgb(0,153,76), null, null)));
                tb2.setBackground(new Background(new BackgroundFill(Color.rgb(0,153,76), null, null)));
            }else{
                if(mode.getValue().toString().equals("Only BlaBlaCar")){
                    radius.setVisible(true);
                    lblRadius.setVisible(true);
                }
                else{
                    radius.setVisible(false);
                    lblRadius.setVisible(false);
                }
                rb1.setVisible(false);
                rb2.setVisible(false);
                tb.setBackground(new Background(new BackgroundFill(Color.rgb(69,106,170), null, null)));
                tb2.setBackground(new Background(new BackgroundFill(Color.rgb(69,106,170), null, null)));
            }
        });

        //Colore 1 mixed mode: EcoBased
        rb1.setOnAction(e->{
            tb.setBackground(new Background(new BackgroundFill(Color.rgb(0,153,76), null, null)));
            tb2.setBackground(new Background(new BackgroundFill(Color.rgb(0,153,76), null, null)));
        });

        //Colore 2 mixed mode: TimeBased
        rb2.setOnAction(e->{
            tb.setBackground(new Background(new BackgroundFill(Color.rgb(153,0,0), null, null)));
            tb2.setBackground(new Background(new BackgroundFill(Color.rgb(153,0,0), null, null)));
        });

        /*ObservableList<String> color =
                FXCollections.observableArrayList(
                        "RED",
                        "BLUE",
                        "YELLOW",
                        "GREY",
                        "WHITE"
                );*/
        //colour = new ComboBox(color);

/**********************************************************************************************************************/
/**                                                    Search Button                                                 **/
        searchButton.setOnAction(e->{

            String color=null;
            String prec=null;

            data2.clear();
            String sl=startLocation.getText().replaceAll(" ","_");
            String fl=finalLocation.getText().replaceAll(" ","_");
            tratteGoogle.clear();
            tratteBlaBla.clear();
            tappeBlaBla.clear();
            viaggio=new Viaggio();
            lc=getTime();
            String dat=calendar.getValue().toString();
            System.out.println("DATAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa; "+dat);

/**********************************************************************************************************************/
/****************************************************** ONLY BLABLACAR ************************************************/
/**********************************************************************************************************************/

            if(mode.getValue().toString().equals("Only BlaBlaCar")){
                scelta=1;
                ContainerAPI container = createContainerTripBlaBla(
                        createPathWithoutCoordinates(
                                sl,
                                fl,
                                dat,
                                lc.getHour(),//Minuti???
                                1,
                                "EUR"
                        )
                );

                System.out.println(container);
                if(container.getTrips().isEmpty()){
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Viaggio non trovato");
                    a.show();
                }else{

                    viaggio=new Viaggio();
                    viaggio=fillTratteBlaBla(container);

                    bp.setLeft(box);
                    DecimalFormat df = new DecimalFormat("####0.00");
                    lbldist.setText("Distance: "+df.format(viaggio.getDistance())+ " km");

                    int ore = (int) viaggio.getDurata() /60;
                    int minuti = (int) viaggio.getDurata()%60;
                    lblduration.setText("Duration: "+ore + "h " + minuti + "'");
                    //lblduration.setText("Duration: "+viaggio.getDurata() +" minutes");

                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°VIAGGIO FINALE!!!!"+viaggio);

                    for(Tratta t: viaggio.getTratte()){

                        do{
                            color=getRandomColour();
                        }while(color==prec);

                        prec=color;

                        setRouteCoordinates(
                                t.getPartenza().getLatitudine(),
                                t.getPartenza().getLongitudine(),
                                t.getDestinazione().getLatitudine(),
                                t.getDestinazione().getLongitudine(),
                                color,
                                TravelModes.DRIVING);

                        t.setColor(color);
                        data2.add(t);
                    }
                }
            }

/**********************************************************************************************************************/
/*************************************************** ONLY PUBLIC TRANSPORT ********************************************/
/**********************************************************************************************************************/

            else if(mode.getValue().toString().equals("Only Public Transport")){

                //Per convertire le coordinate in nome
                Address address = NominatimReverseGeocodingJAPI.getAdress(45.485888, 9.2042827);
                System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% " + address + " +++++++++++++++");

                scelta=2;
                ContainerTrip container = createContainerTrip2(
                        createPath2(
                                sl,
                                fl,
                                dat,
                                lc.getHour(),
                                lc.getMinute()
                        )
                );

                System.out.println(container);
                if(container.getRoutes().isEmpty()){
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Viaggio non trovato");
                    a.show();
                }else {
                    //azzero viaggio
                    viaggio = new Viaggio();
                    viaggio = fillTratteGoogle(container, true);
                    bp.setLeft(box);
                    DecimalFormat df = new DecimalFormat("####0.00");
                    lbldist.setText("Distance: "+df.format(viaggio.getDistance())+ " km");

                    int ore = (int) viaggio.getDurata() /60;
                    int minuti = (int) viaggio.getDurata()%60;
                    lblduration.setText("Duration: "+ore + "h " + minuti + "'");
                    //lblduration.setText("Duration: "+viaggio.getDurata() +" minutes");

                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                            "°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°VIAGGIO FINALE!!!!" + viaggio);

                    for (Tratta t : viaggio.getTratte()) {
                        do{
                            color=getRandomColour();
                        }while(color==prec);
                        prec=color;

                        setRouteCoordinates(
                                t.getPartenza().getLatitudine(),
                                t.getPartenza().getLongitudine(),
                                t.getDestinazione().getLatitudine(),
                                t.getDestinazione().getLongitudine(),
                                color,
                                TravelModes.TRANSIT);

                        t.setColor(color);
                        data2.add(t);
                    }
                }
/**********************************************************************************************************************/
/******************************************************** MIXED MODE **************************************************/
/**********************************************************************************************************************/

            } else if (mode.getValue().toString().equals("Mixed mode")){

                scelta=3;
                Viaggio viaggio_finale;

                container = createContainerTrip2(
                        createPath2(
                                sl,
                                fl,
                                dat,
                                lc.getHour(),
                                lc.getMinute()
                        )
                );

                System.out.println(container); //Print out full container

                if(container.getRoutes().isEmpty()){
                    Alert a=new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Viaggio non trovato");
                    a.show();
                }else {
                    //reset travel
                    viaggio = new Viaggio();
                    viaggio = fillTratteGoogleMixed(container, true, calendar.getValue(), lc);

                    bp.setLeft(box);

                    //algorithm call
                    if(rb1.isSelected())
                        viaggio_finale= Algoritmo.ecoBased(viaggio);
                    else
                        viaggio_finale = Algoritmo.timeBased(viaggio);

                    //display distance and duration on lower bar
                    DecimalFormat df = new DecimalFormat("####0.00");
                    lbldist.setText("Distance: "+df.format(viaggio_finale.getDistance())+ " km");

                    int ore = (int) viaggio.getDurata() /60;
                    int minuti = (int) viaggio.getDurata()%60;
                    lblduration.setText("Duration: "+ore + "h " + minuti + "'");
                   //lblduration.setText("Duration: "+viaggio_finale.getDurata() +" minutes");

                    System.out.println(
                            "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" +
                                    "°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°°VIAGGIO FINALE!!!!" + viaggio_finale);

                    for (Tratta t : viaggio_finale.getTratte()) {
                        if (t.isWithAuto()) {
                            color="RED";
                            setRouteCoordinates(
                                    t.getPartenza().getLatitudine(), t.getPartenza().getLongitudine(),
                                    t.getDestinazione().getLatitudine(), t.getDestinazione().getLongitudine(),
                                    color, TravelModes.DRIVING
                            );

                        } else if(t.isWithFeet()){
                            color="YELLOW";
                            setRouteCoordinates(
                                    t.getPartenza().getLatitudine(), t.getPartenza().getLongitudine(),
                                    t.getDestinazione().getLatitudine(), t.getDestinazione().getLongitudine(),
                                    color, TravelModes.WALKING
                            );
                        }else{
                            color="BLUE";
                            setRouteCoordinates(
                                    t.getPartenza().getLatitudine(), t.getPartenza().getLongitudine(),
                                    t.getDestinazione().getLatitudine(), t.getDestinazione().getLongitudine(),
                                    color, TravelModes.TRANSIT
                            );
                        }
                        t.setColor(color);
                        data2.add(t);
                    }

                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("Travel found with radius "+radius.getText());
                    a.setHeaderText("Congrats!");
                    a.show();
                }
            }

            else{
                Alert a=new Alert(Alert.AlertType.ERROR);
                a.setContentText("Specificy routing algorithm");
                a.show();
            }

            lbldist.setVisible(true);
            lblduration.setVisible(true);
        });

        switchButton.setOnAction(e->{
            String temp;
            temp=startLocation.getText();
            startLocation.setText(finalLocation.getText());
            finalLocation.setText(temp);
        });


/**********************************************************************************************************************/


        refreshAll.setOnAction(e->{
            startLocation.clear();
            finalLocation.clear();
            //hoursTrip.clear();
            calendar.setValue(null);
            time.setValue(null);
            mode.getSelectionModel().selectFirst();
            lbldist.setText("");
            lblduration.setText("");
            mapInitialized();
            mapComponent.getMap().hideDirectionsPane();
            calendar.setValue(LocalDate.now());
            localtime.set(Calendar.getInstance());
            sdf.set(new SimpleDateFormat("HH:mm"));
            time.setValue(sdf.get().format(localtime.get().getTime()));
            bp.setLeft(null);
            data2.clear();

            //renderer.setPanel(null);
            //renderer.setMap(null);
            //renderer=null;

        });

        final boolean[] flag = {true};
        setOptions.setOnAction(e->{
            if(flag[0]) {
                time.setVisible(true);
                calendar.setVisible(true);
                flag[0] =false;
            } else {
                time.setVisible(false);
                calendar.setVisible(false);
                flag[0] =true;
            }
        });


        tb.setMinSize(1,40);
        tb.setAlignment(Pos.CENTER_LEFT);
        tb.setBackground(new Background(new BackgroundFill(Color.rgb(69,106,170), null, null)));//67,130,230

        tb2.setMinSize(1,40);
        tb2.setAlignment(Pos.CENTER);
        tb2.setBackground(new Background(new BackgroundFill(Color.rgb(69,106,170), null, null)));//67,130,230

        tb.getChildren().addAll(/*btnZoomIn, btnZoomOut,*/ new Label(" "),refreshAll,new Label(" "),new Label(" "),/*mapTypeCombo,*/new Label("\t"), lbldist,new Label("\t"), lblduration);
        tb2.getChildren().addAll(setOptions,new Label(" "),startLocation,switchButton,finalLocation,mode,rb1,rb2,new Label("       "),lblRadius,radius,/*hoursTrip,*/new Label(" "),time,calendar/*,colour*/,new Label(" "),searchButton,new Label(" "));
        tb2.setSpacing(5);
        bp.setLeft(null);
        bp.setTop(tb2);
        bp.setBottom(tb);
        bp.setCenter(mapComponent);


        Scene scene = new Scene(bp,1400,800);

        stage.setTitle("A2P Maps");
        stage.setScene(scene);
        stage.show();
    }

    DirectionsRenderer renderer;
    DirectionsRenderer renderer2;

    public LocalTime getTime(){
        String t=time.getValue().toString();
        int hour=Integer.parseInt(t.substring(0,2));
        int min=Integer.parseInt(t.substring(3,5));
        return LocalTime.of(hour,min);
    }

    public LocalTime getTimeByValue(String s){
        int hour=Integer.parseInt(s.substring(0,2));
        int min=Integer.parseInt(s.substring(3,5));
        return LocalTime.of(hour,min);
    }
    static class ColorRectCell extends ListCell<Tratta> {
        @Override
        public void updateItem(Tratta item, boolean empty) {
            super.updateItem(item, empty);
            javafx.scene.shape.Rectangle rect = new Rectangle(30, 30);
            //final StackPane stack = new StackPane();
            final HBox hb=new HBox();
            if (item != null) {
                rect.setFill(Color.web(item.getColor()));
                hb.getChildren().addAll(rect,new Label("  "), new Label(item.forLeftListMini()));
                setGraphic(hb);
            }
        }
    }


    @Override
    public void mapInitialized() {

        //System.out.println("MainApp.mapInitialised....");

        //Once the map has been loaded by the Webview, initialize the map details.
        LatLong center = new LatLong(46.023674, 8.917409);
        mapComponent.addMapReadyListener(() -> {
            // This call will fail unless the map is completely ready.
            checkCenter(center);
        });

        MapOptions options = new MapOptions();
        options.center(center)
                .mapMarker(true)
                .zoom(9)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .mapType(MapTypeIdEnum.TERRAIN);
        //[{\"featureType\":\"landscape\",\"stylers\":[{\"saturation\":-100},{\"lightness\":65},{\"visibility\":\"on\"}]},{\"featureType\":\"poi\",\"stylers\":[{\"saturation\":-100},{\"lightness\":51},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.highway\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"road.arterial\",\"stylers\":[{\"saturation\":-100},{\"lightness\":30},{\"visibility\":\"on\"}]},{\"featureType\":\"road.local\",\"stylers\":[{\"saturation\":-100},{\"lightness\":40},{\"visibility\":\"on\"}]},{\"featureType\":\"transit\",\"stylers\":[{\"saturation\":-100},{\"visibility\":\"simplified\"}]},{\"featureType\":\"administrative.province\",\"stylers\":[{\"visibility\":\"off\"}]},{\"featureType\":\"water\",\"elementType\":\"labels\",\"stylers\":[{\"visibility\":\"on\"},{\"lightness\":-25},{\"saturation\":-100}]},{\"featureType\":\"water\",\"elementType\":\"geometry\",\"stylers\":[{\"hue\":\"#ffff00\"},{\"lightness\":-25},{\"saturation\":-97}]}]
        map = mapComponent.createMap(options,false);
        //directions = mapComponent.getDirec();

        map.setHeading(123.2);
//        System.out.println("Heading is: " + map.getHeading() );


        MarkerOptions markerOptions = new MarkerOptions();
        LatLong markerLatLong = new LatLong(46.023674, 8.917409);//destinazione linea rossa
        markerOptions.position(markerLatLong)
                .title("My new Marker")
                .visible(true);

        final Marker myMarker = new Marker(markerOptions);

        markerOptions2 = new MarkerOptions();
        LatLong markerLatLong2 = new LatLong(46.023674, -122.335842);//partenza linea rossa
        markerOptions2.position(markerLatLong2)
                .title("My new Marker")
                .visible(true);

        myMarker2 = new Marker(markerOptions2);

        //map.addMarker(myMarker);
        //map.addMarker(myMarker2);


        map.fitBounds(new LatLongBounds(new LatLong(30, 120), center));
//        System.out.println("Bounds : " + map.getBounds());

        lblCenter.setText(map.getCenter().toString());
        map.centerProperty().addListener((ObservableValue<? extends LatLong> obs, LatLong o, LatLong n) -> {
            lblCenter.setText(n.toString());
        });

        lblZoom.setText(Integer.toString(map.getZoom()));
        map.zoomProperty().addListener((ObservableValue<? extends Number> obs, Number o, Number n) -> {
            lblZoom.setText(n.toString());
        });


        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            lblClick.setText(ll.toString());
        });

        //btnZoomIn.applyCss();
        mapTypeCombo.setDisable(false);

        mapTypeCombo.getItems().addAll( MapTypeIdEnum.ALL );

        LatLong[] ary = new LatLong[]{markerLatLong, markerLatLong2};



        GeocodingService gs = new GeocodingService();

    }

    public void setRoute(String from, String dest, String colour, TravelModes tr){
        DirectionsService ds = new DirectionsService();//(calcolo delle direzioni)
        renderer = new DirectionsRenderer(true, map, null,colour);//parte grafica strada(visualizzazione)

        renderer.setOptions("true");

        DirectionsRequest dr = new DirectionsRequest(//Partenza e arrivo e come
                from,
                dest,
                tr/////////////////////////////////////////
        );

        ds.getRoute(dr, this, renderer);
    }

    public void setRouteCoordinates(double latorigin, double longorigin, double latdestination, double longdestination,
                                    String colour, TravelModes tr){

        System.out.println(latorigin + "   " + longorigin + "   " + latdestination + "   " + longdestination);

        DirectionsService ds = new DirectionsService();//(calcolo delle direzioni)

        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        renderer = new DirectionsRenderer(true, map, null,colour);//parte grafica strada(visualizzazione)

        renderer.setOptions("true");

        LatLng luogoPartenza = new LatLng(latorigin, longorigin);
        LatLng luogoArrivo = new LatLng(latdestination, longdestination);

        DirectionsRequest dr = new DirectionsRequest(//Partenza e arrivo e come
                luogoPartenza.toString(),
                luogoArrivo.toString(),
                tr/////////////////////////////////////////
        );

        ds.getRoute(dr, this, renderer);
    }

    public String getRandomColour(){
        int randomNum = ThreadLocalRandom.current().nextInt(0,  6);
        String s;
        switch (randomNum){
            case 0:
                s= "BLUE";
                break;
            case 1:
                s= "GREY";
                break;
            case 2:
                s= "GREEN";
                break;
            case 3:
                s= "ORANGE";
                break;
            case 4:
                s= "PINK";
                break;
            case 5:
                s= "RED";
                break;
            default:
                s= "YELLOW";

        }
        return s;
    }


    private void checkCenter(LatLong center) {
//        System.out.println("Testing fromLatLngToPoint using: " + center);
//        Point2D p = map.fromLatLngToPoint(center);
//        System.out.println("Testing fromLatLngToPoint result: " + p);
//        System.out.println("Testing fromLatLngToPoint expected: " + mapComponent.getWidth()/2 + ", " + mapComponent.getHeight()/2);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

    @Override
    public void elevationsReceived(ElevationResult[] results, ElevationStatus status) {
        if(status.equals(ElevationStatus.OK)){
            for(ElevationResult e : results){
                System.out.println(" Elevation on "+ e.getLocation().toString() + " is " + e.getElevation());
            }
        }
    }

    @Override
    public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
        if(status.equals(GeocoderStatus.OK)){
            for(GeocodingResult e : results){
                System.out.println(e.getVariableName());
                System.out.println("GEOCODE: " + e.getFormattedAddress() + "\n" + e.toString());
            }

        }

    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        if(status.equals(DirectionStatus.OK)){
            //mapComponent.getMap().showDirectionsPane();
            System.out.println("OK");

            DirectionsResult e = results;
            GeocodingService gs = new GeocodingService();

            LocalDateTime partenza=null, arrivo=null;



            System.out.println("SIZE ROUTES: " + e.getRoutes().size() + "\n" + "ORIGIN: " + e.getRoutes().get(0).getLegs().get(0).getStartLocation());
            //gs.reverseGeocode(e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLatitude(), e.getRoutes().get(0).getLegs().get(0).getStartLocation().getLongitude(), this);
            System.out.println("LEGS SIZE: " + e.getRoutes().get(0).getLegs().size());
            System.out.println("WAYPOINTS " +e.getGeocodedWaypoints().size());
            int size=e.getRoutes().get(0).getLegs().size();
            //DirectionsRoute route=new DirectionsRoute();
            //route=e.getRoutes().get(0);
            //double totalTime = route.getLegs().stream().mapToDouble(leg -> leg.getDuration().getValue()).sum();
            System.out.println("DURATION "+e.getRoutes().get(0).getLegs().get(0).getDuration().getText());
            NumberFormat formatter=new DecimalFormat("#0.000");

            System.out.println("LEG(0)");
            System.out.println(e.getRoutes().get(0).getLegs().get(0).getSteps().size());
            /*for(DirectionsSteps ds : e.getRoutes().get(0).getLegs().get(0).getSteps()){
                System.out.println(ds.getStartLocation().toString() + " x " + ds.getEndLocation().toString());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(ds.getStartLocation())
                        .title(ds.getInstructions())
                        .animation(Animation.DROP)
                        .visible(true);
                Marker myMarker = new Marker(markerOptions);
                map.addMarker(myMarker);
            }
                    */
            System.out.println(renderer.toString());
        }
        else{
            lbldist.setText("No match");
        }
    }

    /*
        b=true --> se vogliamo riempire anche MainApp.tratteGoogle
     */
    public static Viaggio fillTratteGoogle(ContainerTrip c, boolean b){
        Viaggio v = new Viaggio();

        for(com.lynden.gmapsfx.googleTrips.routes r: c.getRoutes()){
            for (com.lynden.gmapsfx.googleTrips.legs l: r.getLegs()){
                for(com.lynden.gmapsfx.googleTrips.steps s: l.getSteps()){
                    Tratta t = stepToTratta(s);
                    if(t!=null) {
                        if(b){
                            MainApp.tratteGoogle.add(t);
                        }
                        v.aggiungiTratta(t);
                    }
                }
            }
        }

        return v;
    }

    public static Viaggio fillTratteGoogleMixed(ContainerTrip c, boolean b, LocalDate dat, LocalTime lc){
        Viaggio v = new Viaggio();
        Tratta prec=new Tratta(null,null,null,null,dat,lc,null,0);
        int i=0;
        System.out.println("Container: "+c);
        for(com.lynden.gmapsfx.googleTrips.routes r: c.getRoutes()){
            for (com.lynden.gmapsfx.googleTrips.legs l: r.getLegs()){
                for(com.lynden.gmapsfx.googleTrips.steps s: l.getSteps()){
                    Tratta t = stepToTrattaMixed(s,prec);
                    prec=t;
                    if(t!=null) {
                        i++;
                        if(b){
                            MainApp.tratteGoogle.add(t);
                        }
                        v.aggiungiTratta(t);
                    }
                }
            }
        }
        System.out.println("Tratte che aggiunge: "+i);
        System.out.println("Viaggio prima del return : "+v);
        return v;
    }

    public static Viaggio fillTratteBlaBla(ContainerAPI c){
        Viaggio v=new Viaggio();
        for(BlaBlaTrips t:c.getTrips()){
            Tratta k=tripToTratta(t);
            v.aggiungiTratta(k);
        }
        return v;
    }

    public static Tratta tripToTratta(BlaBlaTrips t){
        return new Tratta(new Tappa(t.getDeparture_place()),
                new Tappa(t.getArrival_place()),
                t.getGPartenza(t.getDeparture_date()),t.getOPartenza(t.getDeparture_date()),
                t.getGArrivo(t.getDeparture_date(),(long)t.getDuration().getValue()),t.getOArrivo(t.getDeparture_date(),(long)t.getDuration().getValue()),
                "Auto BLABLA CAR",t.getDistance().getValue());
    }

    public static Tratta stepToTratta(com.lynden.gmapsfx.googleTrips.steps s){
        if(s.getTransit_details()==null){//TODO risolvere sta cosa--> Transit details è a null quando il tratto è a piedi
            System.out.println("TRANSIT DETAILS A NULL -->"+s);
            return null;
        }
        OffsetDateTime odt = OffsetDateTime.now(ZoneId.of(s.getTransit_details().getDeparture_time().getTime_zone()));

        //System.out.println("Fuck: "+transit_details.getDeparture_time().getTime_zone());
        ZoneOffset zoneOffsetDep = odt.getOffset();
        OffsetDateTime odt2 = OffsetDateTime.now(ZoneId.of(s.getTransit_details().getArrival_time().getTime_zone()));
        //System.out.println("Fuck: "+transit_details.getArrival_time().getTime_zone());
        ZoneOffset zoneOffsetArr = odt2.getOffset();
        LocalDateTime dateDep=LocalDateTime.ofEpochSecond(s.getTransit_details().getDeparture_time().getValue(),0, zoneOffsetDep);
        LocalDateTime dateArr=LocalDateTime.ofEpochSecond(s.getTransit_details().getArrival_time().getValue(),0,zoneOffsetArr);
        //LocalDateTime dateDep=LocalDateTime.ofInstant(Instant.ofEpochSecond(transit_details.getDeparture_time().getValue()),ZoneId.of(transit_details.getDeparture_time().getTime_zone()));
        //LocalDateTime dateArr=LocalDateTime.ofInstant(Instant.ofEpochSecond(transit_details.getDeparture_time().getValue()),ZoneId.of(transit_details.getDeparture_time().getTime_zone()));

        LocalDate gP=dateDep.toLocalDate();
        LocalTime oP=dateDep.toLocalTime();
        LocalDate gA=dateArr.toLocalDate();
        LocalTime oA=dateArr.toLocalTime();

        //System.out.println("giorno partenza: " + gP.toString() + "\nora partenza: " + oP.toString() + "\ngiorno arrivo: " + gA.toString() + "\nora arrivo: " + oA.toString());

        System.out.println("\n\n\n\nDIO");
        return new Tratta(new Tappa(s.getStart_location()),new Tappa(s.getEnd_location()), gP, oP,gA,oA,s.getTransit_details().getLine().toString(),s.getDistance().getDouble());
    }

    public static Tratta creaTrattaWalking(com.lynden.gmapsfx.googleTrips.steps s, Tratta prima){
        Tratta t;
        Tappa partenza = new Tappa(s.getStart_location());
        Tappa destinazione = new Tappa(s.getEnd_location());
        LocalDate gP, gA;
        LocalTime oP, oA;
        gP = prima.getGiornoArrivo();
        oP = prima.getOraArrivo();
        double d=0;
        System.out.println("STEPppppppppppppppppppppppppppppppppppppppppppppp: "+s);
        if(s.getSteps()==null)
            d=s.getDuration().getDouble();
        else
            d=s.getSteps().get(0).getDuration().getDouble();
        LocalDateTime part = prima.getTotArrivo().plusSeconds((long) d);
        gA = part.toLocalDate();
        oA = part.toLocalTime();
        double a;
        if(s.getSteps()==null)
            a=s.getDistance().getDouble();
        else
            a=s.getSteps().get(0).getDistance().getDouble();
        double distanza = a;

        String travel;
        if(s.getSteps()==null)
            travel=s.getTravel_mode();
        else
            travel=s.getSteps().get(0).getTravel_mode();

        t = new Tratta(partenza, destinazione, gP, oP, gA, oA,travel, distanza);
        System.out.println("&&&&&&&&&&&&&&&&&&HO CREATO UNA NUOVA TRATTA A PIEDI: \n" + t + "\n\n");
        return t;

    }

    public static Tratta stepToTrattaMixed(com.lynden.gmapsfx.googleTrips.steps s, Tratta prima){
        if(s.getTransit_details()==null){
            //System.out.println("TRANSIT DETAILS A NULL -->"+s);
            //TODO sappiamo che lo step è a piedi quindi va creata una tratta diversa dalle altre
            return creaTrattaWalking(s, prima);
        }
        OffsetDateTime odt = OffsetDateTime.now(ZoneId.of(s.getTransit_details().getDeparture_time().getTime_zone()));

        //System.out.println("Fuck: "+transit_details.getDeparture_time().getTime_zone());
        ZoneOffset zoneOffsetDep = odt.getOffset();
        OffsetDateTime odt2 = OffsetDateTime.now(ZoneId.of(s.getTransit_details().getArrival_time().getTime_zone()));
        //System.out.println("Fuck: "+transit_details.getArrival_time().getTime_zone());
        ZoneOffset zoneOffsetArr = odt2.getOffset();
        LocalDateTime dateDep=LocalDateTime.ofEpochSecond(s.getTransit_details().getDeparture_time().getValue(),0, zoneOffsetDep);
        LocalDateTime dateArr=LocalDateTime.ofEpochSecond(s.getTransit_details().getArrival_time().getValue(),0,zoneOffsetArr);
        //LocalDateTime dateDep=LocalDateTime.ofInstant(Instant.ofEpochSecond(transit_details.getDeparture_time().getValue()),ZoneId.of(transit_details.getDeparture_time().getTime_zone()));
        //LocalDateTime dateArr=LocalDateTime.ofInstant(Instant.ofEpochSecond(transit_details.getDeparture_time().getValue()),ZoneId.of(transit_details.getDeparture_time().getTime_zone()));

        LocalDate gP=dateDep.toLocalDate();
        LocalTime oP=dateDep.toLocalTime();
        LocalDate gA=dateArr.toLocalDate();
        LocalTime oA=dateArr.toLocalTime();

        //System.out.println("giorno partenza: " + gP.toString() + "\nora partenza: " + oP.toString() + "\ngiorno arrivo: " + gA.toString() + "\nora arrivo: " + oA.toString());

        System.out.println("\n\n\n\nDIO");
        return new Tratta(new Tappa(s.getStart_location()),new Tappa(s.getEnd_location()), gP, oP,gA,oA,s.getTransit_details().getLine().toString(),s.getDistance().getDouble());
    }


}
