package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredients_text) TextView mIngredientsText;

    public IngredientsFragment() { }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);
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
        return view;
    }
}
