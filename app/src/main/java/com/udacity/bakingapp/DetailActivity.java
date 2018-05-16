package com.udacity.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.RecipeStep;
import com.udacity.bakingapp.views.DescriptionListFragment;
import com.udacity.bakingapp.views.InstructionFragment;


import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private String mRecipeName;
    private ArrayList<Ingredient> mIngredientList;
    private ArrayList<RecipeStep> mRecipeStepList;

    private int mCurrentPosition;

    private DescriptionListFragment mDescriptionListFragment;
    private InstructionFragment mInstructionFragment;

    private boolean isTablet;

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

        mInstructionFragment = new InstructionFragment();
        mDescriptionListFragment = new DescriptionListFragment();

        isTablet = getResources().getBoolean(R.bool.isTablet);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.recipe_fragment_container, mDescriptionListFragment,
                            getString(R.string.description_list_fragment))
                    .commit();
        }
        setContentView(R.layout.activity_detail);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mCurrentPosition = savedInstanceState.getInt(getString(R.string.position_value));
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void openInstructionFragment() {
        if (isTablet) {
            InstructionFragment visibleFragment = (InstructionFragment)
                    getSupportFragmentManager()
                            .findFragmentByTag(getString(R.string.instruction_fragment));
            if (visibleFragment != null && visibleFragment.isVisible()) {
                visibleFragment.onPositionChanged(mCurrentPosition);
            }
            else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.instruction_fragment_container, mInstructionFragment,
                                getString(R.string.instruction_fragment))
                        .commit();
            }
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_fragment_container, mInstructionFragment,
                            getString(R.string.instruction_fragment))
                    .addToBackStack(null)
                    .commit();
        }
    }

    public String getmRecipeName() {
        return mRecipeName;
    }

    public void setmRecipeName(String mRecipeName) {
        this.mRecipeName = mRecipeName;
    }

    public ArrayList<Ingredient> getmIngredientList() {
        return mIngredientList;
    }

    public void setmIngredientList(ArrayList<Ingredient> mIngredientList) {
        this.mIngredientList = mIngredientList;
    }

    public ArrayList<RecipeStep> getmRecipeStepList() {
        return mRecipeStepList;
    }

    public void setmRecipeStepList(ArrayList<RecipeStep> mRecipeStepList) {
        this.mRecipeStepList = mRecipeStepList;
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public DescriptionListFragment getmDescriptionListFragment() {
        return mDescriptionListFragment;
    }

    public void setmDescriptionListFragment(DescriptionListFragment mDescriptionListFragment) {
        this.mDescriptionListFragment = mDescriptionListFragment;
    }

    public InstructionFragment getmInstructionFragment() {
        return mInstructionFragment;
    }

    public void setmInstructionFragment(InstructionFragment mInstructionFragment) {
        this.mInstructionFragment = mInstructionFragment;
    }
}
