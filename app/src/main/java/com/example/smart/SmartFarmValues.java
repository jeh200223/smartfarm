package com.example.smart;

public class SmartFarmValues {
    private String Temperature;
    private String Humidity;
    private String Water;
    private String Solid;
    private String Co2gas;

    public SmartFarmValues(String temperature, String humidity, String water, String solid, String co2gas) {
        Temperature = temperature;
        Humidity = humidity;
        Water = water;
        Solid = solid;
        Co2gas = co2gas;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }

    public String getSolid() {
        return Solid;
    }

    public void setSolid(String solid) {
        Solid = solid;
    }

    public String getCo2gas() {
        return Co2gas;
    }

    public void setCo2gas(String co2gas) {
        Co2gas = co2gas;
    }
}
