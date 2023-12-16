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

    interface RecyclerViewInterfaceListener{

        void onRecipeSelected(Recipe selectedRecipe);
    }
    public class RecipeViewHolder extends RecyclerView.ViewHolder{

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    RecyclerViewInterfaceListener listener;
    ArrayList<Recipe> recipeList;
    ArrayList<String> list;
    Context context;

    public RecipeListRecyclerAdapter(ArrayList<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    public RecipeListRecyclerAdapter(ArrayList<String> list, Context context, boolean flag) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View recipeRowView;
        if( context.getClass() == MainActivity.class){
            recipeRowView = LayoutInflater.from(context).inflate(R.layout.saved_recipe_row,parent,false);
        }else{
            recipeRowView = LayoutInflater.from(context).inflate(R.layout.searched_recipe_row,parent,false);
        }
        return new RecipeViewHolder(recipeRowView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ImageView img = holder.itemView.findViewById(R.id.RecipeImage);
        TextView recipeName = holder.itemView.findViewById(R.id.recipeNameTextView);

        if( context.getClass() == MainActivity.class){
            img.setImageBitmap(recipeList.get(holder.getAdapterPosition()).getRecipeImage());
            recipeName.setText(recipeList.get(holder.getAdapterPosition()).getRecipeName());
        }else if(context.getClass() == searchRecipeActivity.class){
            recipeName.setText(recipeList.get(holder.getAdapterPosition()).getRecipeName());
        }else {
            recipeName.setText(list.get(holder.getAdapterPosition()));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( context.getClass() != RecipeDetailsActivity.class){
                    listener.onRecipeSelected(recipeList.get(holder.getAdapterPosition()));
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        if( context.getClass() == RecipeDetailsActivity.class){
            return  list.size();
        }else
            return recipeList.size();
    }
}
