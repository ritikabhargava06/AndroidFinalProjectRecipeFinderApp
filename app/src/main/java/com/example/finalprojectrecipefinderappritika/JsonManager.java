package com.example.finalprojectrecipefinderappritika;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonManager {

    public ArrayList<String> convertJsonToCriteriaList(String criteria, String jsonString){
        ArrayList<String> list = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONObject criteriaObj = obj.getJSONObject(criteria);
            JSONArray array = criteriaObj.getJSONArray("data");

            for(int i=0;i<array.length();i++){
                list.add(array.getString(i));
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
