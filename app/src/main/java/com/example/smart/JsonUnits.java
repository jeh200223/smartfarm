package com.example.smart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUnits {

    public ArrayList<SensorValue> getdatas(String json) throws JSONException {
        ArrayList<SensorValue> arrayList = new ArrayList<>();
        ArrayList<SmartFarmValues> arrayList1 = new ArrayList<>();
        String temperature1 = null;
        String humidity1 = null;
        String water1 = null;
        String distance1 = null;
        String Co2gas = null;

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            float temperature = (float) object.getDouble("Temperature");
            float humidity = (float) object.getDouble("Humidity");
            int water = object.getInt("Water");
            int distance = object.getInt("Distance");
            if(object.getString("Co2gas") == null) {
                Co2gas = object.getString("Co2gas");
            }
            temperature1 = String.format("%.1f", temperature);
            humidity1 = String.format("%.1f", humidity);
            water1 = String.format("%d", water);
            distance1 = String.format("%d", distance);
            arrayList.add(new SensorValue(temperature1, humidity1, water1, distance1, Co2gas));
        }
        return arrayList;
    }

    public ArrayList<SmartFarmValues> getdatas2(String json) throws JSONException {
        ArrayList<SensorValue> arrayList = new ArrayList<>();
        ArrayList<SmartFarmValues> arrayList1 = new ArrayList<>();
        String temperaturefarm = null;
        String humidityfarm = null;
        String water1 = null;

        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            float temperature = (float) object.getDouble("Temperature");
            float humidity = (float) object.getDouble("Humidity");
            int water = object.getInt("Waterlevel");
            int soillevel = object.getInt("Soillevel");
            int co2gas = object.getInt("Co2gas");
            temperaturefarm = String.format("%.1f", temperature);
            humidityfarm = String.format("%.1f", humidity);
            water1 = String.format("%d", water);
            String soillevel2 = String.format("%d", soillevel);
            String Co2gasstring = String.format("%d", co2gas);
            arrayList1.add(new SmartFarmValues(temperaturefarm, humidityfarm, water1, soillevel2, Co2gasstring));
        }
        return arrayList1;
    }
}
