package com.example.smart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class GetSensors extends AppCompatActivity {
    String url = "", dates, gas, temperature, humidity;
    ArrayList<SensorValue> arrayList;
    ProgressBar progressBar, progressBar0;
    int valueofwater, valueofdistance, valueofdistanceprogress, percent;
    AsyncTaskClass asyncTaskClass;
    Button button;
    ImageView home;
    ConstraintLayout buttonback;
    TextView textView, textView2, textView3, textViewhum, textView4, textView5;
    Handler handler = new Handler();
    double temperaure_fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barn);
        refresh(1000);
        textView = findViewById(R.id.textview_temp);
        progressBar = findViewById(R.id.progress_bar1);
        textView2 = findViewById(R.id.valueofwater);
        textView3 = findViewById(R.id.valueofdistance);
        textViewhum = findViewById(R.id.textview_humidity);
        textView4 = findViewById(R.id.temperatureprogress);
        textView5 = findViewById(R.id.humidityprogress);
        progressBar0 = findViewById(R.id.progress_bar0);
        home = findViewById(R.id.home);
        button = findViewById(R.id.buttongo);
        buttonback = findViewById(R.id.buttonback);

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetSensors.this, BarnSettings.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetSensors.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void content() {
        asyncTaskClass = new AsyncTaskClass(textView);
        try {
            dates = asyncTaskClass.execute(url).get();
            JsonUnits jsonUnits = new JsonUnits();
            arrayList = jsonUnits.getdatas(dates);
            for (int i = 0; i <arrayList.size(); i++) {
                temperature = arrayList.get(i).getTemperature();
                humidity = arrayList.get(i).getHumidity();
                valueofwater = Integer.parseInt(arrayList.get(i).getWater());
                valueofdistance = Integer.parseInt(arrayList.get(i).getDistance());
                if(valueofdistance < 4) {
                    valueofdistanceprogress = 800;
                } else if (valueofdistance < 9 && valueofdistance >= 4) {
                    valueofdistanceprogress = 500;
                } else if (valueofdistance >= 10) {
                    valueofdistanceprogress = 100;
                }
                progressBar.setProgress(valueofwater);
                progressBar0.setProgress(valueofdistanceprogress);
                percent = valueofwater / 10;
                if(0 <= valueofwater && valueofwater <= 400) {
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarred));
                } else if (400 < valueofwater && valueofwater < 800) {
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbaryellow));
                } else {
                    progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbargreen));
                }
                switch (valueofdistanceprogress) {
                    case 800:
                        progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbargreen));
                        textView3.setText("충분함");
                        break;
                    case 500:
                        progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbaryellow));
                        textView3.setText("보통");
                        break;
                    case 100:
                        progressBar0.setProgressDrawable(getResources().getDrawable(R.drawable.progressbarred));
                        textView3.setText("부족함");
                    default:
                        break;
                }
                textView2.setText(percent + "%");
                textView.setText("온도 : " + temperature + "℃");
                textViewhum.setText("습도 : " + humidity + "%");
                temperaure_fl = Double.parseDouble(temperature);
                gas = arrayList.get(i).getCo2gas();
                if(gas != ""){
                    if(gas == "0"){
                        Toast.makeText(this, "가스가 세고 있습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
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
