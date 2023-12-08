package com.example.finalprojectrecipefinderappritika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
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

public class searchRecipeActivity extends AppCompatActivity implements NetworkingManager.NetworkingManagerInterfaceListener {

    RadioButton categoryRadioButton;
    RadioButton cuisineRadioButton;
    Button searchRecipeButton;
    Spinner criteriaSpinner;
    String searchCriteria="";
    NetworkingManager networkingManager;
    JsonManager jsonManager;
    ArrayList<String> spinnerAdapterDataList = new ArrayList<>();
    ArrayAdapter<String> spinnerAdapter;
    String searchCriteriaType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
        categoryRadioButton=findViewById(R.id.categorySearchCriteria);
        cuisineRadioButton=findViewById(R.id.cuisineSearchCriteria);
        searchRecipeButton = findViewById(R.id.searchbutton);

        criteriaSpinner = findViewById(R.id.searchSpinner);

        networkingManager = ((MyApp)getApplication()).getNetworkingManager();
        networkingManager.listener = this;

        jsonManager = new JsonManager();

        categoryRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    searchCriteria = "categories";
                    networkingManager.getDataFromUrlWith(searchCriteria);
                }
            }
        });

        cuisineRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    searchCriteria = "cuisines";
                    networkingManager.getDataFromUrlWith(searchCriteria);
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
                if (!searchCriteria.isEmpty()){
                    //call api to get recipe list
                }else{
                    //notify user to select a criteris first
                }
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
        spinnerAdapterDataList = jsonManager.convertJsonToCriteriaList(searchCriteria,jsonString);
        //spinnerAdapter.notifyDataSetChanged();
        spinnerAdapter = new ArrayAdapter<>(this,R.layout.criteria_spinner_row,R.id.spinnerRowText,spinnerAdapterDataList);
        criteriaSpinner.setAdapter(spinnerAdapter);
    }
}