package com.udacity.bakingapp.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.bakingapp.R;

import butterknife.BindView;

public class RecipeStepAdapter
        extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    @BindView(R.id.description_list_rv) RecyclerView mRecipeStepRecyclerView;

    public class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder {
        public RecipeStepAdapterViewHolder(View view) {
            super(view);
        }
    }

    @NonNull
    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder
            (@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeStepAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
