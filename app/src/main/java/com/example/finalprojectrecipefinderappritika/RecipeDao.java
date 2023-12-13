package com.example.finalprojectrecipefinderappritika;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert
    void addRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Query("select * from Recipe")
    List<Recipe> getAllRecipes();

    @Query("select * from Recipe where recipeName like :name")
    List<Recipe> getRecipesWith(String name);

    @Query("select * from Recipe where recipeID = :id")
    Recipe getRecipeWith(int id);
}
