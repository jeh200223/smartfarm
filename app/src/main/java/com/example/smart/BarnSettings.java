package com.example.smart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class BarnSettings extends AppCompatActivity {
    SeekBar seekBar1, seekBar2;
    RadioGroup radioGroup;
    String datas, darasofvalue;
    TextView mtextfield, mtextfield2;
    String path = "";
    String url = "";
    String settingtemp = "20", settinghum = "65", settingfood = "1";
    Button buttonsend;
    ImageView home;
    Button button1, button2;
    ConstraintLayout back;
    TextView textView, textView2, textView3;
    String aswitchvalue, aswitchvalue2, aswitchvalue3;
    SwitchCompat switchCompat, switchCompat2, switchCompat3;
    ArrayList<SensorValue> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barn_settings);
        mtextfield = findViewById(R.id.timer);
        mtextfield2 = findViewById(R.id.timerWater);
        aswitchvalue = "0";
        home = findViewById(R.id.home);
        back = findViewById(R.id.buttonback);
        switchCompat2 = findViewById(R.id.switch2);
        switchCompat3 = findViewById(R.id.switch3);
        mtextfield.setVisibility(View.INVISIBLE);
        mtextfield2.setVisibility(View.INVISIBLE);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BarnSettings.this, MainActivity.class);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        seekBar1 = findViewById(R.id.progress);
        seekBar2 = findViewById(R.id.progress2);
        textView2 = findViewById(R.id.temperatureprogress);
        textView3 = findViewById(R.id.humidityprogress);
        switchCompat = findViewById(R.id.switch1);
        AsyncTaskClass asyncTaskClass = new AsyncTaskClass(textView);
        try {
            datas = asyncTaskClass.execute(url).get();
            Log.i("Data", datas);
            JsonUnits jsonUnits = new JsonUnits();
            arrayList = jsonUnits.getdatas(datas);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        AsyncTaskClass asyncTaskClass1 = new AsyncTaskClass(textView);
        try {
            darasofvalue = asyncTaskClass1.execute(path).get();
            JSONArray jsonArray = new JSONArray(darasofvalue);
            aswitchvalue = String.valueOf(jsonArray.get(3));
            Log.i("asdasd", aswitchvalue);
            aswitchvalue2 = String.valueOf(jsonArray.get(4));
            aswitchvalue3 = String.valueOf(jsonArray.get(5));
            if(aswitchvalue.equals("1")){
                switchCompat.setChecked(true);
            } else {
                switchCompat.setChecked(false);
            }

            if(aswitchvalue2.equals("1")){
                switchCompat2.setChecked(true);
            } else {
                switchCompat2.setChecked(false);
            }

            if(aswitchvalue3.equals("1")){
                switchCompat3.setChecked(true);
            } else {
                switchCompat3.setChecked(false);
            }
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        switchCompat2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    mtextfield2.setVisibility(View.VISIBLE);
                    aswitchvalue2 = "1";
                    new CountDownTimer(30000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            mtextfield2.setText(String.format("00:%02d", millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            mtextfield2.setText("done!");
                            mtextfield2.setVisibility(View.INVISIBLE);
                        }
                    }.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            aswitchvalue2 = "0";
                            switchCompat2.setChecked(false);
                            Insertdatatobase();
                        }
                    }, 30000);
                } else {
                    aswitchvalue3 = "0";
                }
            }
        });
        buttonsend = findViewById(R.id.buttongo);
        radioGroup = findViewById(R.id.radio);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView2.setText(String.format("%d℃", progress));
                settingtemp = String.format("%d", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView3.setText(String.format("%d", progress));
                settinghum = String.format("%d", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        Toast.makeText(BarnSettings.this, "3", Toast.LENGTH_SHORT).show();
                        settingfood = "3";
                        break;
                    case R.id.radio2:
                        Toast.makeText(BarnSettings.this, "2", Toast.LENGTH_SHORT).show();
                        settingfood = "2";
                        break;
                    case R.id.radio3:
                        Toast.makeText(BarnSettings.this, "1", Toast.LENGTH_SHORT).show();
                        settingfood = "1";
                        break;
                }
            }
        });
        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InserttoDB inserttoDB = new InserttoDB();
                int settingho = Integer.parseInt(settingtemp);
                int settingshum = Integer.parseInt(settinghum);

                for(int i = 0; i < arrayList.size(); i++) {
                    if(settingshum < Double.parseDouble(arrayList.get(i).getHumidity())){
                        switchCompat.setChecked(true);
                        aswitchvalue = "1";
                    } else {
                        aswitchvalue = "0";
                        switchCompat.setChecked(false);
                    }
                    if(settingho < Double.parseDouble(arrayList.get(i).getTemperature())){
                        switchCompat.setChecked(true);
                        aswitchvalue = "1";
                    } else {
                        aswitchvalue = "0";
                        switchCompat.setChecked(false);
                    }
                    inserttoDB.execute(settingtemp, settinghum, settingfood);
                }
            }
        });
    }

    public void switch_on_button_fan_water_(View view) {
        Insertdatatobase();
    }

    public void Insertdatatobase() {
        Inserttodbfan inserttodbfan = new Inserttodbfan();
        inserttodbfan.execute(aswitchvalue, aswitchvalue2, aswitchvalue3);
    }
}