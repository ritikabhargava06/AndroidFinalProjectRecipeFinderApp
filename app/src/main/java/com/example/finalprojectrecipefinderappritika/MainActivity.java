package com.example.finalprojectrecipefinderappritika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements DatabaseManager.DatabaseManagerListener,
        RecipeListRecyclerAdapter.RecyclerViewInterfaceListener, FireStoreManager.FireStoreManagerInterfaceListener {
    RecyclerView savedRecipesListView;
    FloatingActionButton addNewRecipeButton;
    DatabaseManager mainActivityDbManager;
    FireStoreManager mainActivityFSManager;

    ArrayList<Recipe> recipeArrayList = new ArrayList<>();
    RecipeListRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityDbManager = ((MyApp)getApplication()).getDataBaseManager();
        mainActivityDbManager.getDb(this);

        mainActivityFSManager = ((MyApp)getApplication()).getFireStoreManager();

        savedRecipesListView = findViewById(R.id.favoriteRecipeList);
        addNewRecipeButton = findViewById(R.id.addNewCity);

        addNewRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivityIntent = new Intent(MainActivity.this,searchRecipeActivity.class);
                startActivity(searchActivityIntent);
            }
        });

        ItemTouchHelper.SimpleCallback touchCallBack = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if(((MyApp)getApplication()).getDataSource()==1)
                    mainActivityDbManager.deleteOneRecipeInBackgroundThread(recipeArrayList.get(viewHolder.getAdapterPosition()));
                else if(((MyApp)getApplication()).getDataSource()==2)
                    mainActivityFSManager.deleteRecipeFromFireStoreInBGThread(recipeArrayList.get(viewHolder.getAdapterPosition()));
                recipeArrayList.remove(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchCallBack);
        itemTouchHelper.attachToRecyclerView(savedRecipesListView);

        if(((MyApp)getApplication()).getDataSource()==0){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Where do you want the Recipes from?");
            builder.setPositiveButton("From Room DB", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MyApp)getApplication()).setDataSource(1);
                    mainActivityDbManager.getAllRecipesInBackgroundThread();
                }
            });
            builder.setNegativeButton("From FireStore", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((MyApp)getApplication()).setDataSource(2);
                    mainActivityFSManager.getAllRecipesFromFireStoreInBGThread();
                    //getDataFromFireStore
                }
            });
            builder.create().show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        mainActivityDbManager.listener = this;
        mainActivityFSManager.listener =this;
//        if(adapter!=null && adapter.listener!=this){
//            adapter.listener=this;
//        }

        if(((MyApp)getApplication()).getDataSource()==1)
            mainActivityDbManager.getAllRecipesInBackgroundThread();
        else if(((MyApp)getApplication()).getDataSource()==2){
            mainActivityFSManager.getAllRecipesFromFireStoreInBGThread();
        }
    }

    @Override
    public void notifyIfRecipeAdded(boolean flag) {
        //no need to implementation here
    }

    @Override
    public void finishDBWithRecipesList(List<Recipe> allRecipesList) {
        recipeArrayList = (ArrayList<Recipe>) allRecipesList;
        adapter = new RecipeListRecyclerAdapter(recipeArrayList,this);
        adapter.listener=this;
        savedRecipesListView.setLayoutManager(new LinearLayoutManager(this));
        savedRecipesListView.setAdapter(adapter);
    }

    @Override
    public void finishDBWithRecipeObj(Recipe recipe) {
        //no need of implementation here
    }

    @Override
    public void onRecipeSelected(Recipe selectedRecipe) {
        Intent recipeDetailsIntent = new Intent(this,RecipeDetailsActivity.class);
        recipeDetailsIntent.putExtra("RecipeID",selectedRecipe.getRecipeID());
        startActivity(recipeDetailsIntent);
    }

    @Override
    public void finishFireStoreWithRecipeList(ArrayList<Recipe> recipesList) {
        recipeArrayList = recipesList;
        adapter = new RecipeListRecyclerAdapter(recipeArrayList,this);
        adapter.listener=this;
        savedRecipesListView.setLayoutManager(new LinearLayoutManager(this));
        savedRecipesListView.setAdapter(adapter);
    }

    @Override
    public void finishFireStoreWithRecipe(Recipe r) {
        //no need of implementation here
    }
}