package com.example.finalprojectrecipefinderappritika;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
        @TypeConverter
        public static ArrayList<String> fromString(String value) {
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            return new Gson().fromJson(value, listType);
        }
        @TypeConverter
        public static String fromArrayList(ArrayList<String> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }
        @TypeConverter
        public static String fromBitmapToBase64(Bitmap image)
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            return Base64.encodeToString(b,Base64.DEFAULT);
        }
        @TypeConverter
        public static Bitmap fromBase64toBitMap(String imageString){
            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        }
}


