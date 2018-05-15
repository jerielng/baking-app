package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.RecipeStep;
import com.udacity.bakingapp.views.DescriptionListFragment;
import com.udacity.bakingapp.views.IngredientsFragment;


import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Ingredient> mIngredientList;
    private ArrayList<RecipeStep> mRecipeStepList;

    private String mRecipeName;

    private IngredientsFragment mIngredientsFragment;
    private DescriptionListFragment mDescriptionListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieves Recipe information from MainActivity
        Intent intent = getIntent();
        Recipe recipe = intent.getParcelableExtra(getString(R.string.recipe_object));
        mRecipeName = recipe.getmRecipeName();
        getSupportActionBar().setTitle(mRecipeName);

        mIngredientList = recipe.getmIngredientsList();
        mRecipeStepList = recipe.getmRecipeStepList();

        mIngredientsFragment = new IngredientsFragment();
        mDescriptionListFragment = new DescriptionListFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.ingredients_fragment_container, mIngredientsFragment)
                    .add(R.id.description_list_fragment_container, mDescriptionListFragment)
                    .commit();
        }
        setContentView(R.layout.activity_detail);
    }

    public ArrayList<Ingredient> getmIngredientList() { return mIngredientList; }

    public ArrayList<RecipeStep> getmRecipeStepList() {
        return mRecipeStepList;
    }

    public String getmRecipeName() {
        return mRecipeName;
    }
}
