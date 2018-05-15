package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepDetailActivity;
import com.udacity.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    @BindView(R.id.instruction_text) TextView mInstructionText;

    private StepDetailActivity mParentActivity;
    private int mCurrentPosition;

    public InstructionFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_instruction, container, false);
        ButterKnife.bind(this, view);
        mParentActivity = (StepDetailActivity) getActivity();
        mCurrentPosition = mParentActivity.getmCurrentPosition();
        String instructionString =
                mParentActivity.getmRecipeStepList().get(mCurrentPosition).getmDescription();
        mInstructionText.setText(instructionString);
        return view;
    }

    public void onPositionChanged() {
        mCurrentPosition = mParentActivity.getmCurrentPosition();
        if (mCurrentPosition > -1 &&
                mCurrentPosition < mParentActivity.getmRecipeStepList().size()) {
            RecipeStep newRecipeStep = mParentActivity.getmRecipeStepList().get(mCurrentPosition);
            mInstructionText.setText(newRecipeStep.getmDescription());
        }
    }
}
