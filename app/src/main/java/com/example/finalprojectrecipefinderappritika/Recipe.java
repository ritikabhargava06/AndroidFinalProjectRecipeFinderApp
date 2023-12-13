package com.example.finalprojectrecipefinderappritika;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Recipe {

    @PrimaryKey
    private int recipeID;
    private String recipeName;
    private String category;
    private String cuisine;
    private String time;
    private ArrayList<String> ingredients;
    private ArrayList<String> directions;
    private ArrayList<String> nutrition;
    private Bitmap recipeImage;
    private String recipeDescription;

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public Recipe(int recipeID, String recipeName) {
        this.recipeID = recipeID;
        this.recipeName = recipeName;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIngredientsString() {

        String ingredientsString = "";
        for (String line:ingredients) {
            ingredientsString = ingredientsString+line+"\n";

        }
        return ingredientsString;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }

    public ArrayList<String> getNutrition() {
        return nutrition;
    }

    public String getDirectionsString() {
        String directionsString = "";
        for (String line:directions) {
            directionsString = directionsString+line+"\n";

        }
        return directionsString;
    }

    public void setDirections(ArrayList<String> directions) {
        this.directions = directions;
    }

    public String getNutritionString() {
        String nutritionString = "";
        for (String line:nutrition) {
            nutritionString = nutritionString+line+"\n";

        }
        return nutritionString;
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
