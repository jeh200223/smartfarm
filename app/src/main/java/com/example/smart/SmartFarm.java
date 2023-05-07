package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SmartFarm extends AppCompatActivity {
    ImageView imageViewback;
    Handler handler = new Handler();
    TextView textViewtemperature, textViewhumidity, textViewwater;
    ArrayList<SmartFarmValues> arrayList = new ArrayList<>();
    String water, soild, gas, temperatureget, humidityget;
    String datas;
    ConstraintLayout imageviewback2;
    String url = "";
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_farm_upd);
        imageViewback = findViewById(R.id.home);
        imageviewback2 = findViewById(R.id.buttonback);
        button = findViewById(R.id.buttongo);
        textViewtemperature = findViewById(R.id.textviewtemperature);
        textViewhumidity = findViewById(R.id.textviewhumidity);
        textViewwater = findViewById(R.id.textviewwater);
        imageviewback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartFarm.this, FarmSettings.class);
                startActivity(intent);
            }
        });
    }

    public void content() {
        AsyncTaskClass asyncTaskClass = new AsyncTaskClass(textViewhumidity);
        try {
            datas = asyncTaskClass.execute(url).get();
            JsonUnits jsonUnits = new JsonUnits();
            arrayList = jsonUnits.getdatas2(datas);
            for (int i = 0; i < arrayList.size(); i++) {
                temperatureget = arrayList.get(i).getTemperature();
                humidityget = arrayList.get(i).getHumidity();
                water = arrayList.get(i).getWater();
                double waterint = Integer.parseInt(water) / 10;
                textViewwater.setText(String.format("%.1f", waterint) + "%");
                soild = arrayList.get(i).getSoild();
                gas = arrayList.get(i).getCo2gas();
                textViewtemperature.setText(temperatureget + "°C");
                textViewhumidity.setText(humidityget + "%");
            }
            if(gas == "0") {
                Toast.makeText(this, "가스세고있습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        refresh(1000);
    }
    public void refresh(int miliseconds) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, miliseconds);
    }
}