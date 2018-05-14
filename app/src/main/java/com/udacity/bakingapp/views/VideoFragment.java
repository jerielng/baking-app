package com.udacity.bakingapp.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.udacity.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoFragment extends Fragment {

    @BindView(R.id.video_pv) PlayerView mPlayerView;

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
        ExoPlayer mVideoPlayer;
        return view;
    }
}
