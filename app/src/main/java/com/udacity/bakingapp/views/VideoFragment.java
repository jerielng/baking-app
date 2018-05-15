package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.StepDetailActivity;
import com.udacity.bakingapp.model.RecipeStep;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {

    private SimpleExoPlayer mExoPlayer;
    @BindView(R.id.video_pv) PlayerView mPlayerView;
    @BindView(R.id.placeholder_text) TextView mPlaceholderText;

    private StepDetailActivity mParentActivity;
    private int mCurrentPosition;

    public VideoFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, view);

        mParentActivity = (StepDetailActivity) getActivity();
        mCurrentPosition = mParentActivity.getmCurrentPosition();

        initializePlayer(extractVideoLink());

        return view;
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

    public void onPositionChanged() {
        if (mExoPlayer != null) { mExoPlayer.release(); }
        mCurrentPosition = mParentActivity.getmCurrentPosition();
        initializePlayer(extractVideoLink());
    }
}
