package com.example.finalprojectrecipefinderappritika;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class RecipeDialogFragment extends DialogFragment {

    interface RecipeFragmentListener{
        void saveSelectedRecipe(Recipe recipe);
    }
    static Recipe recipe;
    RecipeFragmentListener listener;
    public static RecipeDialogFragment newInstance(Recipe selectedRecipeObj){
        recipe = selectedRecipeObj;
        return new RecipeDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View recipeView = inflater.inflate(R.layout.recipe_fragment,container,false);
        TextView recipeNameText = recipeView.findViewById(R.id.selectedRecipeName);
        ImageView imageView = recipeView.findViewById(R.id.selectedRecipeImage);
        TextView descText = recipeView.findViewById(R.id.selectedRecipeDesc);
        Button saveB = recipeView.findViewById(R.id.saveRecipeButton);
        Button ignoreB = recipeView.findViewById(R.id.ignoreButton);

        recipeNameText.setText(recipe.getRecipeName());
        imageView.setImageBitmap(recipe.getRecipeImage());
        descText.setText(recipe.getRecipeDescription());

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.saveSelectedRecipe(recipe);
                dismiss();
            }
        });

        ignoreB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return recipeView;
    }
}
