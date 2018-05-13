package com.udacity.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String recipeLink
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            RecipeListFragment recipeList = new RecipeListFragment();
            getSupportFragmentManager()
                    .beginTransaction().add(R.id.main_layout, recipeList).commit();
        }
        setContentView(R.layout.activity_main);
    }
}
