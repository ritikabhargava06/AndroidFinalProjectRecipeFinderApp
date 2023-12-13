package com.example.finalprojectrecipefinderappritika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements DatabaseManager.DatabaseManagerListener, RecipeListRecyclerAdapter.RecyclerViewInterfaceListener {

    TextView recipeNameText;
    ImageView recipeImage;
    TextView recipeCategoryText;
    TextView recipeCuisineText;
    TextView recipePrepTimeText;
    TextView recipeNutritionText;
    RecyclerView recipeIngredientsRecycView;
    RecyclerView recipeDirectionsRecycView;
    DatabaseManager recipeDetailsDbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeNameText = findViewById(R.id.recipeDetailsRecipeName);
        recipeImage = findViewById(R.id.recipeDetailsImage);
        recipeCategoryText = findViewById(R.id.recipeDetailsCategory);
        recipeCuisineText = findViewById(R.id.recipeDetailsCuisine);
        recipePrepTimeText = findViewById(R.id.RecipeDetailsPrepTime);
        recipeNutritionText = findViewById(R.id.RecipeDetailsNutrition);
        recipeIngredientsRecycView = findViewById(R.id.RecipeDetailsIngredients);
        recipeDirectionsRecycView = findViewById(R.id.RecipeDetailsDirections);
        recipeDetailsDbManager = ((MyApp)getApplication()).getDataBaseManager();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recipeDetailsDbManager.listener=this;
        int selectedRecipeId = getIntent().getExtras().getInt("RecipeID");
        recipeDetailsDbManager.getOneRecipeInBackgroundThreadWith(selectedRecipeId);
    }

    @Override
    public void notifyIfRecipeAdded(boolean flag) {
        //no need of implementation here
    }

    @Override
    public void finishDBWithRecipesList(List<Recipe> allRecipesList) {
        //no need of implementation here
    }

    @Override
    public void finishDBWithRecipeObj(Recipe selectedRecipe) {
        recipeNameText.setText(selectedRecipe.getRecipeName());
        recipeImage.setImageBitmap(selectedRecipe.getRecipeImage());
        recipeCategoryText.setText(String.format("Category: %s", selectedRecipe.getCategory()));
        recipeCuisineText.setText(String.format("Cuisine: %s", selectedRecipe.getCuisine()));
        recipePrepTimeText.setText(selectedRecipe.getTime());
        recipeNutritionText.setText(String.format("Nutrition:\n%s", selectedRecipe.getNutritionString()));

        RecipeListRecyclerAdapter ingredAdap = new RecipeListRecyclerAdapter(selectedRecipe.getIngredients(),this,false);
        ingredAdap.listener = this;
        recipeIngredientsRecycView.setLayoutManager(new LinearLayoutManager(this));
        recipeIngredientsRecycView.setAdapter(ingredAdap);

        RecipeListRecyclerAdapter direcAdap = new RecipeListRecyclerAdapter(selectedRecipe.getDirections(),this,false);
        direcAdap.listener = this;
        recipeDirectionsRecycView.setLayoutManager(new LinearLayoutManager(this));
        recipeDirectionsRecycView.setAdapter(direcAdap);
        //recipeIngredientsText.setText("Ingredients:\n"+selectedRecipe.getIngredientsString());
        //recipeDirectionsText.setText("Directions:\n"+selectedRecipe.getDirectionsString());
    }

    @Override
    public void onRecipeSelected(Recipe selectedRecipe) {
        //no need of implementation here
    }
}