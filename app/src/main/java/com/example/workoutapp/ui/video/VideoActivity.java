package com.example.workoutapp.ui.video;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.WindowManager;

import com.example.workoutapp.R;
import com.example.workoutapp.base.BaseActivity;
import com.example.workoutapp.databinding.ActivityVideoBinding;
import com.example.workoutapp.model.VideoModel;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoActivity extends BaseActivity implements com.example.workoutapp.ui.video.VideoView {
    ActivityVideoBinding binding;
    private VideoPresenter presenter;
    private VideoModel video;

    public static void startVideoActivity(Context context, VideoModel model) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("VIDEO", model);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void onViewCreated() {
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        video = (VideoModel) getIntent().getSerializableExtra("VIDEO");

        presenter = new VideoPresenter(this);
        presenter.isWatched(video);
    }

    @Override
    public void onReceiveData(boolean result) {
        hideLoading();
        if (result) {
            PlayerView playerView = binding.playerView;

            SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "Workout App"));

            MediaSource videoSource =
                    new ProgressiveMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(video.getUrl()));

            player.prepare(videoSource);
        }
    }
}
