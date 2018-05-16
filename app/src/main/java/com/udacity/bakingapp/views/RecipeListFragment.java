package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.MainActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.RecipeAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment {

    @BindView(R.id.recipe_card_rv) RecyclerView mCardRecyclerView;
    private RecyclerView.Adapter mCardAdapter;
    private RecyclerView.LayoutManager mCardLayoutManager;

    public RecipeListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, view);

        mCardRecyclerView.setHasFixedSize(true);

        if (MainActivity.isTablet) {
            mCardLayoutManager = new GridLayoutManager(getContext(), 3,
                    LinearLayoutManager.VERTICAL, false);
            mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        } else {
            mCardLayoutManager = new LinearLayoutManager(getContext());
            mCardRecyclerView.setLayoutManager(mCardLayoutManager);
        }

        new FetchRecipesTask().execute();

        return view;
    }

    public URL buildRecipeUrl() {
        Uri recipeUri = Uri.parse(MainActivity.recipeLink);
        try {
            return new URL(recipeUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class FetchRecipesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL recipeUrl = buildRecipeUrl();
                HttpURLConnection urlConnection = (HttpURLConnection) recipeUrl.openConnection();
                try {
                    InputStream in = urlConnection.getInputStream();
                    Scanner scanner = new Scanner(in);
                    scanner.useDelimiter("\\A");
                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        return scanner.next();
                    } else {
                        return null;
                    }
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String jsonString) {
            mCardAdapter = new RecipeAdapter(getContext(), jsonString);
            mCardRecyclerView.setAdapter(mCardAdapter);
        }
    }
}
