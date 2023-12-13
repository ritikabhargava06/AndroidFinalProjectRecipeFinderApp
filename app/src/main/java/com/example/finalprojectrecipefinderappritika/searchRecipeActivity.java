package com.example.finalprojectrecipefinderappritika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class searchRecipeActivity extends AppCompatActivity implements NetworkingManager.NetworkingManagerInterfaceListener,
        RecipeListRecyclerAdapter.RecyclerViewInterfaceListener, RecipeDialogFragment.RecipeFragmentListener, DatabaseManager.DatabaseManagerListener{

    RadioButton categoryRadioButton;
    RadioButton cuisineRadioButton;
    Button searchRecipeButton;
    Spinner criteriaSpinner;
    RecyclerView searchedRecipesListView;
    String searchCriteria="";
    NetworkingManager networkingManager;
    DatabaseManager dbManager;
    ArrayList<String> spinnerAdapterDataList = new ArrayList<>();
    ArrayList<Recipe> recyclerAdapterDataList = new ArrayList<>();
    ArrayList<Recipe> searchedRecipeList = new ArrayList<>();

    ArrayAdapter<String> spinnerAdapter;
    RecipeListRecyclerAdapter recyclerAdapter;
    String searchCriteriaType="";
    boolean searchButtonClicked = false;
    Recipe selectedRecipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
        categoryRadioButton=findViewById(R.id.categorySearchCriteria);
        cuisineRadioButton=findViewById(R.id.cuisineSearchCriteria);
        searchRecipeButton = findViewById(R.id.searchbutton);

        criteriaSpinner = findViewById(R.id.searchSpinner);
        searchedRecipesListView = findViewById(R.id.searchedRecipeList);


        networkingManager = ((MyApp)getApplication()).getNetworkingManager();
        networkingManager.listener = this;

        dbManager = ((MyApp)getApplication()).getDataBaseManager();
        //dbManager.getDb(this);
        dbManager.listener = this;

        categoryRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(searchButtonClicked){
                        searchButtonClicked=false;
                    }
                    searchCriteria = "categories";
                    networkingManager.getDataFromAllInOneRecipeApiWith(searchCriteria);
                }
            }
        });

        cuisineRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(searchButtonClicked){
                        searchButtonClicked=false;
                    }
                    searchCriteria = "cuisines";
                    networkingManager.getDataFromAllInOneRecipeApiWith(searchCriteria);
                }
            }
        });

        criteriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchCriteriaType = spinnerAdapterDataList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //we don't need to implement this
            }
        });

        searchRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //notify user to select a criteria first
                if (!searchCriteria.isEmpty()){
                    //call api to get recipe list
                    searchButtonClicked = true;
                    String criteria = searchCriteria+"/"+searchCriteriaType;
                    networkingManager.getDataFromAllInOneRecipeApiWith(criteria);
                }else
                    Toast.makeText(getApplicationContext(), "Please choose a search criteria first", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchBar = (SearchView)menu.findItem(R.id.searchbar_menu_item).getActionView();
        searchBar.setQueryHint("Search for Recipe");
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter the available list with keyword and refresh adapter with new list
                if(newText.length()>3){
                    ArrayList<Recipe> list = new ArrayList<>();
                    for (Recipe r:searchedRecipeList) {
                        if(r.getRecipeName().toLowerCase().contains(newText.toLowerCase())){
                            list.add(r);
                        }
                    }
                    recyclerAdapterDataList = list;
                }else{
                    recyclerAdapterDataList = searchedRecipeList;
                }
                if(recyclerAdapter!=null){
                    recyclerAdapter = new RecipeListRecyclerAdapter(recyclerAdapterDataList,searchRecipeActivity.this);
                    recyclerAdapter.listener = searchRecipeActivity.this;
                    searchedRecipesListView.setLayoutManager(new LinearLayoutManager(searchRecipeActivity.this));
                    searchedRecipesListView.setAdapter(recyclerAdapter);
                    //recyclerAdapter.notifyDataSetChanged();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishAPICallwithJsonString(String jsonString) {

        if (searchButtonClicked){
                searchedRecipeList = MyApp.jsonManager.convertJsonToRecipeObjectList(searchCriteria,jsonString);
                recyclerAdapterDataList = searchedRecipeList;
                recyclerAdapter = new RecipeListRecyclerAdapter(recyclerAdapterDataList,this);
                recyclerAdapter.listener=this;
                searchedRecipesListView.setLayoutManager(new LinearLayoutManager(this));
                searchedRecipesListView.setAdapter(recyclerAdapter);
        }else{
            spinnerAdapterDataList = MyApp.jsonManager.convertJsonToCriteriaList(searchCriteria,jsonString);
            //spinnerAdapter.notifyDataSetChanged();
            spinnerAdapter = new ArrayAdapter<>(this,R.layout.criteria_spinner_row,R.id.spinnerRowText,spinnerAdapterDataList);
            criteriaSpinner.setAdapter(spinnerAdapter);
        }

    }

    @Override
    public void finishAPICallWithUpdatedRecipeObject(Recipe updatedRecipe) {
        RecipeDialogFragment df = RecipeDialogFragment.newInstance(selectedRecipe);
        df.listener = this;
        df.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onRecipeSelected(Recipe obj) {
        selectedRecipe = obj;
        networkingManager.getRecipeDetailsFromAPICall(selectedRecipe);
    }

    @Override
    public void saveSelectedRecipe(Recipe recipe) {
        dbManager.addRecipeInBGThread(recipe);
    }

    @Override
    public void notifyIfRecipeAdded(boolean flag) {
        if(flag){
            Toast.makeText(searchRecipeActivity.this,"Recipe has been added to your Favorites list",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(searchRecipeActivity.this,"Recipe already exist in your Favourites List",Toast.LENGTH_LONG).show();
        }
        finish();
    }

    @Override
    public void finishDBWithRecipesList(List<Recipe> allRecipesList) {
        //no need of implementation here
    }

    @Override
    public void finishDBWithRecipeObj(Recipe recipe) {
        //no need of implementation here
    }
}