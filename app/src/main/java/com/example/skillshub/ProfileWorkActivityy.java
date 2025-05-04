package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileWorkActivityy extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_work_activityy);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile); // Ensure profile is selected by default
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(ProfileWorkActivityy.this, MainActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(ProfileWorkActivityy.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_work) {
            startActivity(new Intent(ProfileWorkActivityy.this, WorkActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_notifications) {
            startActivity(new Intent(ProfileWorkActivityy.this, NotificationsActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_profile) {
            return true;  // Already in Profile Work Activity
        }

        return false;
    }
}
