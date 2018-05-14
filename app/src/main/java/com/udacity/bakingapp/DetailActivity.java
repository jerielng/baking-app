package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.RecipeStep;
import com.udacity.bakingapp.views.IngredientsFragment;


import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Ingredient> mIngredientList;
    private ArrayList<RecipeStep> mRecipeStepList;

    private IngredientsFragment mIngredientsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieves Recipe information from MainActivity
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(getString(R.string.recipe_object));
        getSupportActionBar().setTitle(recipe.getmRecipeName());

        mIngredientList = recipe.getmIngredientsList();
        mRecipeStepList = recipe.getmRecipeStepList();

        mIngredientsFragment = new IngredientsFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_layout, mIngredientsFragment)
                    .commit();
        }
        setContentView(R.layout.activity_detail);
    }

    public ArrayList<Ingredient> getmIngredientList() {
        return mIngredientList;
    }

    public ArrayList<RecipeStep> getmRecipeStepList() {
        return mRecipeStepList;
    }
}
