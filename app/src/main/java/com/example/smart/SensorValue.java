package com.example.smart;

public class SensorValue {
    private String Temperature;
    private String Humidity;
    private String Water;
    private String Distance;
    private String Co2gas;

    public SensorValue(String temperature, String humidity, String water, String distance, String co2gas) {
        Temperature = temperature;
        Humidity = humidity;
        Water = water;
        Distance = distance;
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

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getCo2gas() {
        return Co2gas;
    }

    public void setCo2gas(String co2gas) {
        Co2gas = co2gas;
    }
}
