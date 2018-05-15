package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepNavigationFragment extends Fragment {

    @BindView(R.id.prev_step_button) Button mPreviousButton;
    @BindView(R.id.next_step_button) Button mNextButton;
    @BindView(R.id.current_step_text) TextView mCurrentStepText;

    private StepDetailActivity mParentActivity;
    private int mCurrentPosition;

    public StepNavigationFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_step_navigation, container, false);
        ButterKnife.bind(this, view);
        mParentActivity = (StepDetailActivity) getActivity();
        mCurrentPosition = mParentActivity.getmCurrentPosition();
        setListeners();
        updateViews();
        return view;
    }

    public void setListeners() {
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition > 0) mCurrentPosition--;
                mParentActivity.setmCurrentPosition(mCurrentPosition);
                updateViews();
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition < mParentActivity.getmRecipeStepList().size() - 1) {
                    mCurrentPosition++;
                }
                mParentActivity.setmCurrentPosition(mCurrentPosition);
                updateViews();
            }
        });
    }

    public void updateViews() {
        //Update page number view
        String currentPositionText = "Step " + Integer.toString(mCurrentPosition);
        mCurrentStepText.setText(currentPositionText);

        //Update visibility of buttons based on position in recipe step list
        if (mCurrentPosition == 0) {
            mPreviousButton.setVisibility(View.INVISIBLE);
        } else if (mCurrentPosition == mParentActivity.getmRecipeStepList().size() - 1) {
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        }

        //Signals to other Activity Fragments that position has changed
        mParentActivity.getmInstructionFragment().onPositionChanged();
        mParentActivity.getmVideoFragment().onPositionChanged();
    }
}
