package com.lynden.gmapsfx.googleTrips;

public class duration {
    private String text;

    @Override
    public String toString() {
        return "duration{" +
                "text='" + text + '\'' +
                '}';
    }

    public double getDouble(){
        String t;
        if(text.contains("min")){
            t=text.replaceAll(" min","");
            t=t.replaceAll("\\.","");
            t=t.replaceAll(",",".");

            return Double.parseDouble(t)*60;
        }
        else if(text.contains("sec")){
            t=text.replaceAll(" sec","");
            t=t.replaceAll("\\.","");
            t=t.replaceAll(",",".");

            return Double.parseDouble(t);
        }
        t=text.replaceAll(" hour","");
        t=t.replaceAll(",",".");
        System.out.println("\n\n"+text);
        return Double.parseDouble(t)*60*60;
    }
}
