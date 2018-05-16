package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.RecipeStepAdapter;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.RecipeStep;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DescriptionListFragment extends Fragment {

    @BindView(R.id.ingredients_text) TextView mIngredientsText;

    @BindView(R.id.description_list_rv) RecyclerView mRecipeStepRecyclerView;
    private RecipeStepAdapter mRecipeStepAdapter;
    private RecyclerView.LayoutManager mStepCardLayoutManager;

    public DescriptionListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_description_list, container, false);
        ButterKnife.bind(this, view);

        populateIngredients();

        mRecipeStepRecyclerView.setHasFixedSize(true);

        ArrayList<RecipeStep> recipeStepList =
                ((DetailActivity) getActivity()).getmRecipeStepList();
        String recipeName = ((DetailActivity) getActivity()).getmRecipeName();

        mRecipeStepAdapter = new RecipeStepAdapter(getContext(), recipeName, recipeStepList);
        mRecipeStepRecyclerView.setAdapter(mRecipeStepAdapter);

        mStepCardLayoutManager = new LinearLayoutManager(getContext());
        mRecipeStepRecyclerView.setLayoutManager(mStepCardLayoutManager);

        return view;
    }

    public void populateIngredients() {
        ArrayList<Ingredient> ingredients = ((DetailActivity) getActivity()).getmIngredientList();
        mIngredientsText.setText(getString(R.string.ingredients_text) + "\n");
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient appendIngredient = ingredients.get(i);
            String ingredientLine =
                    "\n(" + appendIngredient.getmQuantity() + " "
                            + appendIngredient.getmMeasure() + ") "
                            + appendIngredient.getmIngredientName();
            mIngredientsText.append(ingredientLine);
        }
    }
}
