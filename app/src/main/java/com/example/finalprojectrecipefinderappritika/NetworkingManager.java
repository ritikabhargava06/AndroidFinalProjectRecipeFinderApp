package com.example.finalprojectrecipefinderappritika;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkingManager {

interface NetworkingManagerInterfaceListener{
    void finishAPICallwithJsonString(String jsonString);
    void finishAPICallWithUpdatedRecipeObject(Recipe updatedRecipe);
}
    NetworkingManagerInterfaceListener listener;
    private static final String KEY = "e366dbc069msha7e8a02338e4adfp1d3bf8jsna07bcacbea31";
    //private static final String HOST = "all-in-one-recipe-api.p.rapidapi.com";
    //pulkit - e366dbc069msha7e8a02338e4adfp1d3bf8jsna07bcacbea31
    //ritika - afbec7e028msh3aae26a14e91b3ap18e4ddjsn8176476a08b6
    //shefali - 01f799b91bmsh7517433646d2970p13579ejsn279ae9e4aa92

    void getDataFromAllInOneRecipeApiWith(String criteria){
        String URLString = "https://all-in-one-recipe-api.p.rapidapi.com";
        String HOST = "all-in-one-recipe-api.p.rapidapi.com";
        String urlString = URLString+"/"+criteria;
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    int value;
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
                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                    assert urlConnection != null;
                    urlConnection.disconnect();
                }

            }
        });
    }

    void getRecipeDetailsFromAPICall(Recipe recipe){
        String URLString = "https://all-in-one-recipe-api.p.rapidapi.com/";
        String HOST = "all-in-one-recipe-api.p.rapidapi.com";
        String urlString = URLString+"details/"+recipe.getRecipeID();
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection;
                int value;
                try {

                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("X-RapidAPI-Key", KEY);
                    urlConnection.setRequestProperty("X-RapidAPI-Host", HOST);
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    StringBuffer buffer = new StringBuffer();
                    while((value = in.read()) != -1){
                        buffer.append((char)value);
                    }

                    Recipe updatedRecipe = MyApp.jsonManager.setRecipeDetailsFromJson(buffer.toString(), recipe);
                    Bitmap recipeImage = getDataFromWorldWideRecipeApiFor(updatedRecipe.getRecipeName());
                    updatedRecipe.setRecipeImage(recipeImage);

                    MyApp.handler.post(new Runnable() {
                        @Override
                        public void run() {
                           listener.finishAPICallWithUpdatedRecipeObject(updatedRecipe);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    Bitmap getDataFromWorldWideRecipeApiFor(String recipeName){
        String urlString = "https://worldwide-recipes1.p.rapidapi.com/api/search?q=";
        String urlStr = urlString+recipeName;
        String HOST = "worldwide-recipes1.p.rapidapi.com";
        Bitmap imgBitmap;

        //MyApp.executorService.execute(new Runnable() {
           // @Override
           // public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    int value;
                    URL url = new URL(urlStr);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestProperty("X-RapidAPI-Key",KEY);
                    httpURLConnection.setRequestProperty("X-RapidAPI-Host",HOST);
                    //httpURLConnection
                    InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                    StringBuffer buffer = new StringBuffer();
                    while ((value=in.read()) != -1){
                        buffer.append((char)value);
                    }

                    String imgURL = MyApp.jsonManager.getRecipeImageUrlFromJson(buffer.toString());
                    InputStream is;
                    is = (InputStream) new URL(imgURL).getContent();
                    imgBitmap = BitmapFactory.decodeStream(is);
//                    MyApp.handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.d("Recipe Name Data",buffer.toString());
//                            listener.finishWorldWideAPIwithJSONString(buffer.toString());
//                        }
//                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    assert httpURLConnection != null;
                    httpURLConnection.disconnect();
                }
           // }
       // });

        return imgBitmap;
    }
}
