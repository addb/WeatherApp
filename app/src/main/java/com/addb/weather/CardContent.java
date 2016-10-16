package com.addb.weather;

/**
 * Created by Arjun on 10/15/2016.
 */

public class CardContent {
    String day;
    String temperature;
    String scale;

    public CardContent(String day, String temperature, String scale){
        this.day = day;
        this.temperature = temperature;
        this.scale = scale;
    }
    public String getDay(){
        return this.day;
    }
    public String getScale(){
        return this.scale;
    }
    public String getTemperature(){
        return this.temperature;
    }

    public void setDay(String day){
        this.day = day;
    }
    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
    public void setScale(String scale){
        this.scale = scale;
    }
}

