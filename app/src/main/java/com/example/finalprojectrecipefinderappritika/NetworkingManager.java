package com.example.finalprojectrecipefinderappritika;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkingManager {

interface NetworkingManagerInterfaceListener{
    void finishAPICallwithJsonString(String jsonString);
}

    String URLString = "https://all-in-one-recipe-api.p.rapidapi.com";
    NetworkingManagerInterfaceListener listener;
    private static final String KEY = "afbec7e028msh3aae26a14e91b3ap18e4ddjsn8176476a08b6";
    private static final String HOST = "all-in-one-recipe-api.p.rapidapi.com";

    void getDataFromUrlWith(String criteria){
        String urlString = URLString+"/"+criteria;
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    int value = 0;
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("X-RapidAPI-Key", KEY);
                    urlConnection.setRequestProperty("X-RapidAPI-Host", HOST);
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    StringBuffer buffer = new StringBuffer();
                    while((value = in.read()) != -1){
                        buffer.append((char)value);
                    }
                    MyApp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d("data from api",buffer.toString());
                           listener.finishAPICallwithJsonString(buffer.toString());
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    urlConnection.disconnect();
                }

            }
        });
    }
}
