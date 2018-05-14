package com.udacity.bakingapp;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.bakingapp.views.DescriptionListFragment;
import com.udacity.bakingapp.views.StepNavigationFragment;
import com.udacity.bakingapp.views.VideoFragment;

public class StepDetailActivity extends AppCompatActivity {

    private DescriptionListFragment mDescriptionListFragment;
    private VideoFragment mVideoFragment;
    private StepNavigationFragment mStepNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescriptionListFragment = new DescriptionListFragment();
        mVideoFragment = new VideoFragment();
        mStepNavigationFragment = new StepNavigationFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_layout, mVideoFragment)
                    .add(R.id.detail_layout, mDescriptionListFragment)
                    .add(R.id.detail_layout, mStepNavigationFragment)
                    .commit();
        }

        setContentView(R.layout.activity_step_detail);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.detail_layout, mVideoFragment)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_layout, mDescriptionListFragment)
                    .add(R.id.detail_layout, mStepNavigationFragment)
                    .commit();
        }
    }
}
