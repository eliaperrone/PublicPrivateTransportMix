package com.lynden.gmapsfx.googleTrips;

public class distance {
    private String text;

    @Override
    public String toString() {
        return "distance{" +
                "text='" + text + '\'' +
                '}';
    }

    public double getDouble(){
        String t;
        if(text.contains("mi")){
            t=text.replaceAll(" mi","");
            t=t.replaceAll("\\.","");
            t=t.replaceAll(",",".");

            System.out.println("\n\n"+text);
            return Double.parseDouble(t)*1.6;
        }else if(text.contains("km")){
            t=text.replaceAll(" km","");
            t=t.replaceAll(",",".");
            System.out.println("\n\n"+text);
            return Double.parseDouble(t);
        }
        else{
            t=text.replaceAll(" m","");
            t=t.replaceAll(",",".");
            System.out.println("\n\n"+text);
            return Double.parseDouble(t)/1000;
        }

    }
}