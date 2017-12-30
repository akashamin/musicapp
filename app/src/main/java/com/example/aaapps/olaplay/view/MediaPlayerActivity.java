package com.example.aaapps.olaplay.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aaapps.olaplay.R;
import com.example.aaapps.olaplay.presenter.SongPresenter;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaPlayerActivity extends AppCompatActivity {

    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView mExoPlayerView;

    BandwidthMeter mBandwidthMeter;
    ExtractorsFactory mExtractorsFactory;
    TrackSelection.Factory mTrackSelectionFactory;
    TrackSelector mTrackSelector;
    DefaultBandwidthMeter mDefaultBandwidthMeter;
    DefaultHttpDataSourceFactory mHttpDataSourceFactory;
    DefaultDataSourceFactory mDefaultDataSourceFactory;
    MediaSource mMediaSource;
    SimpleExoPlayer mSimpleExoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        ButterKnife.bind(this);

        String songurl = getIntent().getStringExtra("songurl");

        mBandwidthMeter = new DefaultBandwidthMeter();
        mExtractorsFactory = new DefaultExtractorsFactory();
        mTrackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(mTrackSelectionFactory);

        mDefaultBandwidthMeter = new DefaultBandwidthMeter();

        mHttpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(this, "OLAPlay"),
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true /* allowCrossProtocolRedirects */
        );

        mDefaultDataSourceFactory = new DefaultDataSourceFactory(
                this,
                null /* listener */,
                mHttpDataSourceFactory
        );
        mMediaSource = new ExtractorMediaSource(Uri.parse(songurl), mDefaultDataSourceFactory, mExtractorsFactory, null, null);

        mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, mTrackSelector);
        mSimpleExoPlayer.prepare(mMediaSource);
        mSimpleExoPlayer.setPlayWhenReady(true);
        mExoPlayerView.setPlayer(mSimpleExoPlayer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSimpleExoPlayer.setPlayWhenReady(false);
        mSimpleExoPlayer.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSimpleExoPlayer.setPlayWhenReady(false);
        mSimpleExoPlayer.release();
    }
}
