package com.example.skillshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationsActivity extends AppCompatActivity {

    private VideoView backgroundVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the default ActionBar (removes white bar with title)
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_notifications);

        setupBackgroundVideo();
        setupBottomNavigation();
        setupRecyclerView();
        setupBrowseOpportunities();
    }

    private void setupBackgroundVideo() {
        backgroundVideoView = findViewById(R.id.bg_video);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifications12);
        backgroundVideoView.setVideoURI(videoUri);
        backgroundVideoView.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f);
        });
        backgroundVideoView.start();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_notifications);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_ideas) {
                startActivity(new Intent(this, LearnActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_work) {
                startActivity(new Intent(this, WorkActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_notifications) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.notification_recycler_view);
        // TODO: Initialize your adapter and layout manager here
    }

    private void setupBrowseOpportunities() {
        TextView browseText = findViewById(R.id.browse_opportunities);
        browseText.setOnClickListener(v -> {
            Intent intent = new Intent(this, WorkActivity.class);
            startActivity(intent);
        });
    }
}
