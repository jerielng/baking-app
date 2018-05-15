package com.udacity.bakingapp.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepDetailActivity;
import com.udacity.bakingapp.model.RecipeStep;

import java.util.ArrayList;

public class RecipeStepAdapter
        extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    private Context mContext;
    private String mRecipeName;
    private ArrayList<RecipeStep> mRecipeStepList;

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView mStepView;
        public RecipeStepAdapterViewHolder(TextView view) {
            super(view);
            mStepView = view;
        }
    }

    public RecipeStepAdapter(Context context, String recipeName,
                             ArrayList<RecipeStep> recipeStepList) {
        mContext = context;
        mRecipeName = recipeName;
        mRecipeStepList = recipeStepList;
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        TextView descriptionView = new TextView(mContext);
        RecipeStepAdapterViewHolder recipeStepCard
                = new RecipeStepAdapterViewHolder(descriptionView);
        return recipeStepCard;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, final int position) {
        final RecipeStep recipeStepAt = mRecipeStepList.get(position);
        holder.mStepView.setText(recipeStepAt.getmShortDescription());
        holder.mStepView.setTextAppearance(mContext, R.style.StepCardText);
        holder.mStepView.setBackgroundResource(R.color.cardBack);
        holder.mStepView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        holder.mStepView.setPadding(0, 20, 0, 20);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(20, 10, 20, 10);
        holder.mStepView.setLayoutParams(cardParams);
        holder.mStepView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stepDetailIntent = new Intent(mContext, StepDetailActivity.class);
                stepDetailIntent.putExtra
                        (mContext.getString(R.string.recipe_name), mRecipeName);
                stepDetailIntent.putExtra
                        (mContext.getString(R.string.recipe_step_list), mRecipeStepList);
                stepDetailIntent.putExtra
                        (mContext.getString(R.string.position_value), position);
                mContext.startActivity(stepDetailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeStepList.size();
    }
}
