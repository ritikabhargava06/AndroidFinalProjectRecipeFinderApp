package com.example.finalprojectrecipefinderappritika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView savedRecipesListView;
    FloatingActionButton addNewRecipeButton;

    ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NetworkingManager nwm = new NetworkingManager();
        //nwm.getDataFrom();
        savedRecipesListView = findViewById(R.id.favoriteRecipeList);
        addNewRecipeButton = findViewById(R.id.addNewCity);

        RecipeListRecyclerAdapter adapter = new RecipeListRecyclerAdapter(recipeArrayList,this);
        savedRecipesListView.setAdapter(adapter);
        savedRecipesListView.setLayoutManager(new LinearLayoutManager(this));

        addNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivityIntent = new Intent(MainActivity.this,searchRecipeActivity.class);
                startActivity(searchActivityIntent);
            }
        });

    }
}