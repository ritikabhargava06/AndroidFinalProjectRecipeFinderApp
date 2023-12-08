package com.example.finalprojectrecipefinderappritika;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Recipe {

    private String recipeName;
    private String category;
    private String cuisine;
    private String[] time;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private ArrayList<String> nutrition;
    Bitmap recipeImage;


    public Recipe(String recipeName, String category, String cuisine) {
        this.recipeName = recipeName;
        this.category = category;
        this.cuisine = cuisine;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public ArrayList<String> getNutrition() {
        return nutrition;
    }

    public void setNutrition(ArrayList<String> nutrition) {
        this.nutrition = nutrition;
    }

    public Bitmap getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(Bitmap recipeImage) {
        this.recipeImage = recipeImage;
    }



}
