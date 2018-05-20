package com.udacity.bakingapp.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.RecipeStep;

import java.util.ArrayList;

public class RecipeStepAdapter
        extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    private Context mContext;
    private String mRecipeName;
    private ArrayList<RecipeStep> mRecipeStepList;

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mStepContainer;
        public ImageView mThumbnailView;
        public TextView mStepText;
        public RecipeStepAdapterViewHolder(LinearLayout view) {
            super(view);
            mStepContainer = view;
            mThumbnailView = new ImageView(mContext);
            mStepText = new TextView(mContext);

            mStepContainer.addView(mThumbnailView);
            mStepContainer.addView(mStepText);
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
        LinearLayout stepContainer = new LinearLayout(mContext);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.setMargins(20, 10, 20, 10);
        stepContainer.setLayoutParams(cardParams);
        stepContainer.setBackgroundResource(R.color.cardBack);

        RecipeStepAdapterViewHolder recipeStepCard
                = new RecipeStepAdapterViewHolder(stepContainer);
        return recipeStepCard;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, final int position) {
        final RecipeStep recipeStepAt = mRecipeStepList.get(position);

        holder.mStepContainer.setContentDescription(recipeStepAt.getmShortDescription());

        LinearLayout.LayoutParams thumbnailParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        thumbnailParams.weight = 3f;
        thumbnailParams.gravity = Gravity.CENTER;
        holder.mThumbnailView.setLayoutParams(thumbnailParams);
        Picasso.get()
                .load(Uri.parse(recipeStepAt.getmThumbnailUrl()))
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .into(holder.mThumbnailView);

        LinearLayout.LayoutParams textParams =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.weight = 1f;
        textParams.gravity = Gravity.CENTER;
        holder.mStepText.setLayoutParams(textParams);
        holder.mStepText.setText(recipeStepAt.getmShortDescription());
        holder.mStepText.setTextAppearance(mContext, R.style.StepCardText);
        holder.mStepText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        holder.mStepContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity currentActivity = (DetailActivity) mContext;
                currentActivity.setmCurrentPosition(position);
                currentActivity.openInstructionFragment();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipeStepList.size();
    }
}
