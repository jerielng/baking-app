package com.udacity.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            DescriptionListFragment descriptionFragment = new DescriptionListFragment();
            VideoFragment videoFragment = new VideoFragment();
            InstructionFragment instructionFragment = new InstructionFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_layout, descriptionFragment)
                    .add(R.id.detail_layout, videoFragment)
                    .add(R.id.detail_layout, instructionFragment)
                    .commit();
        }
        getSupportActionBar().setTitle("PLACEHOLDER TITLE");
        setContentView(R.layout.activity_detail);
    }
}
