package com.udacity.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    public Context mContext;
    private ArrayList<String> mRecipeList;

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public RecipeAdapterViewHolder(TextView view) {
            super(view);
            mTitleView = view;
        }
    }

    public RecipeAdapter(Context context, String jsonString) {
        mContext = context;
        mRecipeList = new ArrayList<>();
        extractRecipes(jsonString);
    }

    @NonNull
    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView titleView = new TextView(mContext);
        RecipeAdapterViewHolder recipeCard = new RecipeAdapterViewHolder(titleView);
        return recipeCard;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, int position) {
        holder.mTitleView.setText(mRecipeList.get(position));
        holder.mTitleView.setTextAppearance(mContext, R.style.RecipeCardText);
        holder.mTitleView.setBackgroundResource(R.color.cardBack);
        holder.mTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mTitleView.setPadding(0, 200, 0, 200);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(20, 20, 20, 5);
        holder.mTitleView.setLayoutParams(cardParams);
        holder.mTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, DetailActivity.class);
//                detailIntent.putExtra();
                mContext.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void extractRecipes(String jsonResponse) {
        try {
            JSONArray recipeJson = new JSONArray(jsonResponse);
            for (int i = 0; i < recipeJson.length(); i++) {
                mRecipeList.add(recipeJson.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
