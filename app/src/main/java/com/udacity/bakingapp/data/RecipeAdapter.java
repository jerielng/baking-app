package com.udacity.bakingapp.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.Ingredient;
import com.udacity.bakingapp.model.Recipe;
import com.udacity.bakingapp.model.RecipeStep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    public Context mContext;
    private ArrayList<Recipe> mRecipeList;

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
    public void onBindViewHolder(@NonNull RecipeAdapterViewHolder holder, final int position) {
        final Recipe recipeAt = mRecipeList.get(position);
        holder.mTitleView.setText(recipeAt.getmRecipeName());
        holder.mTitleView.setTextAppearance(mContext, R.style.RecipeCardText);
        holder.mTitleView.setBackgroundResource(R.color.cardBack);
        holder.mTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mTitleView.setPadding(0, 200, 0, 200);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(20, 20, 20, 20);
        holder.mTitleView.setLayoutParams(cardParams);
        holder.mTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(mContext, DetailActivity.class);
                detailIntent.putExtra
                        (mContext.getString(R.string.recipe_object), recipeAt);
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
                String newRecipeName = recipeJson.getJSONObject(i)
                        .getString(mContext.getString(R.string.name_param));
                JSONArray ingredientsJson
                        = recipeJson.getJSONObject(i)
                        .getJSONArray(mContext.getString(R.string.ingredients_param));
                JSONArray recipeStepsJson
                        = recipeJson.getJSONObject(i)
                        .getJSONArray(mContext.getString(R.string.steps_param));
                ArrayList<Ingredient> mIngredientList = new ArrayList<>();
                ArrayList<RecipeStep> mRecipeStepList = new ArrayList<>();
                for (int j = 0; j < ingredientsJson.length(); j++) {
                    JSONObject objectAt = ingredientsJson.getJSONObject(j);
                    double newQuantity =
                            objectAt.getDouble(mContext.getString(R.string.quantity_param));
                    String newMeasure =
                            objectAt.getString(mContext.getString(R.string.measure_param));
                    String newIngredientName =
                            objectAt.getString(mContext.getString(R.string.ingredient_param));
                    Ingredient newIngredient
                            = new Ingredient(newQuantity, newMeasure, newIngredientName);
                    mIngredientList.add(newIngredient);
                }
                for (int k = 0; k < recipeStepsJson.length(); k++) {
                    JSONObject objectAt = recipeStepsJson.getJSONObject(k);
                    int newId =
                            objectAt.getInt(mContext.getString(R.string.id_param));
                    String newShortDescription =
                            objectAt.getString
                                    (mContext.getString(R.string.short_description_param));
                    String newDescription =
                            objectAt.getString(mContext.getString(R.string.description_param));
                    String newVideoUrl =
                            objectAt.getString(mContext.getString(R.string.video_param));
                    String newThumbnailUrl =
                            objectAt.getString(mContext.getString(R.string.thumbnail_param));
                    RecipeStep newRecipeStep
                            = new RecipeStep(newId, newShortDescription,
                            newDescription, newVideoUrl, newThumbnailUrl);
                    mRecipeStepList.add(newRecipeStep);
                }
                mRecipeList.add(new Recipe(newRecipeName, mIngredientList, mRecipeStepList));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
