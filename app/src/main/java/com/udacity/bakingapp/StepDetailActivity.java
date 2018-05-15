package com.udacity.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.model.RecipeStep;
import com.udacity.bakingapp.views.InstructionFragment;
import com.udacity.bakingapp.views.StepNavigationFragment;
import com.udacity.bakingapp.views.VideoFragment;

import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {

    private VideoFragment mVideoFragment;
    private InstructionFragment mInstructionFragment;
    private StepNavigationFragment mStepNavigationFragment;

    private String mRecipeName;
    private int mCurrentPosition;
    private ArrayList<RecipeStep> mRecipeStepList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieves Recipe information from MainActivity
        Intent intent = getIntent();
        mRecipeName =
                intent.getStringExtra(getString(R.string.recipe_name));
        mRecipeStepList =
                intent.getParcelableArrayListExtra(getString(R.string.recipe_step_list));
        mCurrentPosition = intent.getIntExtra(getString(R.string.position_value), 0);

        mVideoFragment = new VideoFragment();
        mInstructionFragment = new InstructionFragment();
        mStepNavigationFragment = new StepNavigationFragment();

        getSupportActionBar().setTitle(mRecipeName);

        if (savedInstanceState == null) {
            if (getApplicationContext().getResources().getConfiguration().orientation
                    == Configuration.ORIENTATION_PORTRAIT) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.video_container, mVideoFragment)
                        .add(R.id.instruction_container, mInstructionFragment)
                        .add(R.id.navigation_container, mStepNavigationFragment)
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.video_container, mVideoFragment)
                        .commit();
            }
        }

        setContentView(R.layout.activity_step_detail);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), mCurrentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCurrentPosition = savedInstanceState.getInt(getString(R.string.position_value));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mInstructionFragment = new InstructionFragment();
            mStepNavigationFragment = new StepNavigationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.instruction_container, mInstructionFragment)
                    .add(R.id.navigation_container, mStepNavigationFragment)
                    .commit();
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mInstructionFragment)
                    .remove(mStepNavigationFragment)
                    .commit();
        }
        setContentView(R.layout.activity_step_detail);
    }

    public VideoFragment getmVideoFragment() {
        return mVideoFragment;
    }

    public void setmVideoFragment(VideoFragment mVideoFragment) {
        this.mVideoFragment = mVideoFragment;
    }

    public InstructionFragment getmInstructionFragment() {
        return mInstructionFragment;
    }

    public void setmInstructionFragment(InstructionFragment mInstructionFragment) {
        this.mInstructionFragment = mInstructionFragment;
    }

    public StepNavigationFragment getmStepNavigationFragment() {
        return mStepNavigationFragment;
    }

    public void setmStepNavigationFragment(StepNavigationFragment mStepNavigationFragment) {
        this.mStepNavigationFragment = mStepNavigationFragment;
    }

    public int getmCurrentPosition() {
        return mCurrentPosition;
    }

    public void setmCurrentPosition(int mCurrentPosition) {
        this.mCurrentPosition = mCurrentPosition;
    }

    public ArrayList<RecipeStep> getmRecipeStepList() {
        return mRecipeStepList;
    }

    public void setmRecipeStepList(ArrayList<RecipeStep> mRecipeStepList) {
        this.mRecipeStepList = mRecipeStepList;
    }
}
