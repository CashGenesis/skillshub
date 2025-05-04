package com.example.skillshub;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        VideoView videoView = findViewById(R.id.backgroundVideo);

        // Set video URI from raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_vid);
        videoView.setVideoURI(videoUri);

        // Configure video behavior
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // Loop video
            mp.setVolume(0f, 0f); // Mute video

            // Stretch video to fit screen and crop if needed
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        });

        videoView.start();
    }
}
