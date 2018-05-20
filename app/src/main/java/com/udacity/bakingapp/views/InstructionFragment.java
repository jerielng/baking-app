package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.video_pv) PlayerView mPlayerView;
    @BindView(R.id.thumbnail_container) ImageView mThumbnailView;

    @BindView(R.id.instruction_text) TextView mInstructionText;

    @BindView(R.id.navigation_container) FrameLayout mNavigationContainer;
    @BindView(R.id.prev_step_button) Button mPreviousButton;
    @BindView(R.id.next_step_button) Button mNextButton;
    @BindView(R.id.current_step_text) TextView mCurrentStepText;

    private DetailActivity mParentActivity;
    private int mCurrentPosition;
    private long mLastPlayerPosition;
    private boolean mLastPlayerPlayState;

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
        setThumbnailView(extractThumbnailLink());
        initializePlayer(extractVideoLink());
        setListeners();
        updateViews();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mExoPlayer == null) {
            initializePlayer(extractVideoLink());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mExoPlayer == null) {
            initializePlayer(extractVideoLink());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseExoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseExoPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(getString(R.string.position_value), mCurrentPosition);
        if (mExoPlayer != null) {
            outState.putLong(getString(R.string.player_position), mExoPlayer.getCurrentPosition());
            outState.putBoolean(getString(R.string.player_play_state),
                    mExoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(getString(R.string.position_value));
            mLastPlayerPosition = savedInstanceState.getLong(getString(R.string.player_position));
            mLastPlayerPlayState = savedInstanceState.
                    getBoolean(getString(R.string.player_play_state));
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
                resetPlayerState();

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
                resetPlayerState();

                //Signals to other Activity Fragments that position has changed
                onPositionChanged(mCurrentPosition);
            }
        });
    }

    public void onPositionChanged(int newPosition) {
        mCurrentPosition = newPosition;

        releaseExoPlayer();
        initializePlayer(extractVideoLink());
        mExoPlayer.seekTo(mLastPlayerPosition);
        mExoPlayer.setPlayWhenReady(mLastPlayerPlayState);

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
            mNextButton.setVisibility(View.VISIBLE);
        } else if (mCurrentPosition == mParentActivity.getmRecipeStepList().size() - 1) {
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mPreviousButton.setVisibility(View.VISIBLE);
            mNextButton.setVisibility(View.VISIBLE);
        }
    }

    public String extractVideoLink() {
        RecipeStep currentStep = mParentActivity.getmRecipeStepList().get(mCurrentPosition);
        String videoUrl = currentStep.getmVideoUrl();
        if (TextUtils.isEmpty(videoUrl)) {
            videoUrl = currentStep.getmThumbnailUrl(); //Check if thumbnail URL contains video
        }
        return videoUrl;
    }

    public String extractThumbnailLink() {
        RecipeStep currentStep = mParentActivity.getmRecipeStepList().get(mCurrentPosition);
        return currentStep.getmThumbnailUrl();
    }

    public void initializePlayer(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            mPlayerView.setVisibility(View.GONE);
            mThumbnailView.setVisibility(View.VISIBLE);
        } else {
            mThumbnailView.setVisibility(View.GONE);
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

    public void setThumbnailView(String thumbnailUrl) {
        Picasso.get()
                .load(Uri.parse(thumbnailUrl))
                .placeholder(R.drawable.default_icon)
                .error(R.drawable.default_icon)
                .into(mThumbnailView);
    }

    public void releaseExoPlayer() {
        if (mExoPlayer != null) { mExoPlayer.release(); }
    }

    public void resetPlayerState() {
        mLastPlayerPosition = 0;
        mLastPlayerPlayState = false;
    }
}
