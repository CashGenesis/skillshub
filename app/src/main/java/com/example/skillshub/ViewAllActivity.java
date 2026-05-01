package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private RecyclerView connectionsRecycler;
    private ConnectionsAdapter adapter;
    private List<ConnectionItem> connectionsList;
    private ImageView btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_view_all);

        // Initialize views
        connectionsRecycler = findViewById(R.id.connections_recycler);
        btnClose = findViewById(R.id.btn_close);

        // Setup RecyclerView
        connectionsRecycler.setLayoutManager(new LinearLayoutManager(this));
        
        // Initialize data
        initializeConnections();
        
        // Setup adapter
        adapter = new ConnectionsAdapter(this, connectionsList);
        connectionsRecycler.setAdapter(adapter);

        // Setup bottom navigation
        setupBottomNavigation();

        // Close button
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeConnections() {
        connectionsList = new ArrayList<>();

        connectionsList.add(new ConnectionItem(
                "Elena Vance",
                "San Francisco, CA",
                94,
                "UI Design",
                "Motion Graphics",
                "Backend Dev",
                "Rust",
                R.drawable.profilepic
        ));

        connectionsList.add(new ConnectionItem(
                "Marcus Thorne",
                "London, UK",
                88,
                "Python ML",
                "",
                "React Native",
                "Swift",
                R.drawable.profilepic
        ));

        connectionsList.add(new ConnectionItem(
                "Sarah Chen",
                "New York, NY",
                92,
                "Graphic Design",
                "Branding",
                "Web Dev",
                "JavaScript",
                R.drawable.profilepic
        ));
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_match);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(ViewAllActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(ViewAllActivity.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        } else if (itemId == R.id.nav_work) {
            startActivity(new Intent(ViewAllActivity.this, WorkActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        } else if (itemId == R.id.nav_match) {
            startActivity(new Intent(ViewAllActivity.this, MatchActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(ViewAllActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            finish();
            return true;
        }

        return false;
    }

    // Connection Item Model
    public static class ConnectionItem {
        private String name;
        private String location;
        private int matchPercentage;
        private String teaches1;
        private String teaches2;
        private String wants1;
        private String wants2;
        private int profileImage;

        public ConnectionItem(String name, String location, int matchPercentage,
                            String teaches1, String teaches2, String wants1, String wants2,
                            int profileImage) {
            this.name = name;
            this.location = location;
            this.matchPercentage = matchPercentage;
            this.teaches1 = teaches1;
            this.teaches2 = teaches2;
            this.wants1 = wants1;
            this.wants2 = wants2;
            this.profileImage = profileImage;
        }

        // Getters
        public String getName() { return name; }
        public String getLocation() { return location; }
        public int getMatchPercentage() { return matchPercentage; }
        public String getTeaches1() { return teaches1; }
        public String getTeaches2() { return teaches2; }
        public String getWants1() { return wants1; }
        public String getWants2() { return wants2; }
        public int getProfileImage() { return profileImage; }
    }
}
