package com.example.finalprojectrecipefinderappritika;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeListRecyclerAdapter extends RecyclerView.Adapter {

    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    ArrayList<Recipe> recipeList;
    Context context;

    public RecipeListRecyclerAdapter(ArrayList<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeRowView = LayoutInflater.from(context).inflate(R.layout.saved_recipe_row,parent,false);
        return new RecipeViewHolder(recipeRowView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.RecipeImage);
        TextView recipeName = holder.itemView.findViewById(R.id.recipeNameTextView);

        img.setImageBitmap(recipeList.get(holder.getAdapterPosition()).getRecipeImage());
        recipeName.setText(recipeList.get(holder.getAdapterPosition()).getRecipeName());
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
