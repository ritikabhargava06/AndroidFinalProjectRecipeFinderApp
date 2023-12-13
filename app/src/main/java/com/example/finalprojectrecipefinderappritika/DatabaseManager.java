package com.example.finalprojectrecipefinderappritika;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class DatabaseManager {

    interface DatabaseManagerListener{
        void notifyIfRecipeAdded(boolean flag);

        void finishDBWithRecipesList(List<Recipe> allRecipesList);

        void finishDBWithRecipeObj(Recipe recipe);
    }

    RecipeDatabase db;
    DatabaseManagerListener listener;
    RecipeDatabase getDb(Context context){
        if(db==null){
            db = Room.databaseBuilder(context, RecipeDatabase.class,"Recipe-database").build();
        }
        return db;
    }

    void addRecipeInBGThread(Recipe toBeAdded){
        MyApp.executorService.execute(new Runnable() {
            boolean recipeAdded = false;
            @Override
            public void run() {
                List<Recipe> recipes = searchRecipeWith(toBeAdded.getRecipeName());
                if(recipes.size()==0){
                    db.getDao().addRecipe(toBeAdded);
                    recipeAdded=true;
                }
                MyApp.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.notifyIfRecipeAdded(recipeAdded);
                    }
                });
            }
        });
    }

    List<Recipe> searchRecipeWith(String name){

        return db.getDao().getRecipesWith(name);
    }

    void getAllRecipesInBackgroundThread(){
        MyApp.executorService.execute(new Runnable() {
            List<Recipe> allRecipesList = null;
            @Override
            public void run() {
                allRecipesList = db.getDao().getAllRecipes();
                MyApp.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.finishDBWithRecipesList(allRecipesList);
                    }
                });
            }
        });
    }

    void deleteOneRecipeInBackgroundThread(Recipe toBeDelete){
        MyApp.executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteRecipe(toBeDelete);
            }
        });
    }

    void getOneRecipeInBackgroundThreadWith(int recipeID){
        MyApp.executorService.execute(new Runnable() {
            Recipe recipe;
            @Override
            public void run() {
                recipe = db.getDao().getRecipeWith(recipeID);
                MyApp.handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.finishDBWithRecipeObj(recipe);
                    }
                });
            }
        });
    }
}
