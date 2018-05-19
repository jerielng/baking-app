package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.video_pv) PlayerView mPlayerView;
    @BindView(R.id.placeholder_text) TextView mPlaceholderText;

    @BindView(R.id.instruction_text) TextView mInstructionText;

    @BindView(R.id.navigation_container) FrameLayout mNavigationContainer;
    @BindView(R.id.prev_step_button) Button mPreviousButton;
    @BindView(R.id.next_step_button) Button mNextButton;
    @BindView(R.id.current_step_text) TextView mCurrentStepText;

    private DetailActivity mParentActivity;
    private int mCurrentPosition;
    private long mLastPlayerPosition;

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
        mParentActivity = (DetailActivity) getActivity();
        mCurrentPosition = mParentActivity.getmCurrentPosition();
        String instructionString =
                mParentActivity.getmRecipeStepList().get(mCurrentPosition).getmDescription();
        mInstructionText.setText(instructionString);
        initializePlayer(extractVideoLink());
        setListeners();
        updateViews();
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), mCurrentPosition);
        if (mExoPlayer != null) {
            outState.putLong(getString(R.string.player_position), mExoPlayer.getCurrentPosition());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(getString(R.string.position_value));
            mLastPlayerPosition = savedInstanceState.getLong(getString(R.string.player_position));
            onPositionChanged(mCurrentPosition);
        }
    }

    public void setListeners() {
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition > 0) {
                    mCurrentPosition--;
                }
                updateViews();
                resetPlayerPosition();

                //Signals to other Activity Fragments that position has changed
                onPositionChanged(mCurrentPosition);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPosition < mParentActivity.getmRecipeStepList().size() - 1) {
                    mCurrentPosition++;
                }
                updateViews();
                resetPlayerPosition();

                //Signals to other Activity Fragments that position has changed
                onPositionChanged(mCurrentPosition);
            }
        });
    }

    public void onPositionChanged(int newPosition) {
        mCurrentPosition = newPosition;

        if (mExoPlayer != null) { mExoPlayer.release(); }
        initializePlayer(extractVideoLink());
        mExoPlayer.seekTo(mLastPlayerPosition);

        /* Sets text for instruction TextView */
        if (mCurrentPosition > -1 &&
                mCurrentPosition < mParentActivity.getmRecipeStepList().size()) {
            RecipeStep newRecipeStep = mParentActivity.getmRecipeStepList().get(mCurrentPosition);
            mInstructionText.setText(newRecipeStep.getmDescription());
        }

        updateViews();
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
    }

    public String extractVideoLink() {
        RecipeStep currentStep = mParentActivity.getmRecipeStepList().get(mCurrentPosition);
        String videoUrl = currentStep.getmVideoUrl();
        if (videoUrl.equals("")) {
            videoUrl = currentStep.getmThumbnailUrl();
        }
        return videoUrl;
    }

    public void initializePlayer(String videoUrl) {
        if (videoUrl.equals("")) {
            mPlayerView.setVisibility(View.GONE);
            mPlaceholderText.setVisibility(View.VISIBLE);
        } else {
            mPlaceholderText.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            Uri videoUri = Uri.parse(videoUrl);
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            mExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(mExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoUri);
            mExoPlayer.prepare(mediaSource);
        }
    }

    public void resetPlayerPosition() {
        mLastPlayerPosition = 0;
    }
}
