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

    public ArrayList<Recipe> convertJsonToRecipeObjectList(String criteria, String jsonString){
        ArrayList<Recipe> recipeArrayList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONObject criteriaObj = obj.getJSONObject(criteria);
            JSONArray array = criteriaObj.getJSONArray("data");

            for(int i=0;i<array.length();i++){
                if(!array.get(i).equals(null)){
                    JSONObject jsonObj = array.getJSONObject(i);
                    Recipe recipeObj = new Recipe(jsonObj.getInt("id"),jsonObj.getString("name"));
                    recipeArrayList.add(recipeObj);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return recipeArrayList;
    }

    public Recipe setRecipeDetailsFromJson(String jsonString, Recipe recipe){

        try {
            String desc = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").getString("Description");
            recipe.setRecipeDescription(desc);

            JSONArray timeArray = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").
                    getJSONArray("Time");

            String prepTime = timeArray.getString(0)+"\n"+timeArray.getString(1);
            recipe.setTime(prepTime);

            JSONArray ingredientsArray = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").
                    getJSONArray("Ingredients");

            ArrayList<String> ingredientsList = new ArrayList<>();
            for(int i=0;i<ingredientsArray.length();i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
            recipe.setIngredients(ingredientsList);

            JSONArray directionsArray = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").
                    getJSONArray("Directions");

            ArrayList<String> directionsList = new ArrayList<>();
            for(int i=0;i<directionsArray.length();i++){
                directionsList.add(directionsArray.getString(i));
            }
            recipe.setDirections(directionsList);

            JSONArray nutritionArray = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").
                    getJSONArray("Nutritions");

            ArrayList<String> nutritionList = new ArrayList<>();
            for(int i=0;i<nutritionArray.length();i++){
                nutritionList.add(nutritionArray.getString(i));
            }
            recipe.setNutrition(nutritionList);

            String category = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").getString("Category");
            recipe.setCategory(category);
            String cuisine = new JSONObject(jsonString).getJSONObject("recipe").
                    getJSONObject("data").getString("Cuisine");
            recipe.setCuisine(cuisine);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return  recipe;
    }

    public String getRecipeImageUrlFromJson(String jsonString){
        String imageUrlString;
        try {
            JSONArray feedsArray = new JSONObject(jsonString).getJSONObject("results").getJSONArray("feed");
            imageUrlString = feedsArray.getJSONObject(0).getJSONObject("seo").getJSONObject("web").getString("image-url");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return imageUrlString;
    }

}
