package com.example.smart;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Inserttodbfan extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        String path = "";
        String fan, waterpump, motor;
        fan = strings[0];
        waterpump = strings[1];
        motor = strings[2];

        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
            String daat = URLEncoder.encode("Fan", "UTF-8") + "=" + URLEncoder.encode(fan, "UTF-8") + "&" +
                    URLEncoder.encode("WaterPump", "UTF-8") + "=" + URLEncoder.encode(waterpump, "UTF-8") + "&" +
                    URLEncoder.encode("Motor", "UTF-8") + "=" + URLEncoder.encode(motor, "UTF-8");
            bufferedWriter.write(daat);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
