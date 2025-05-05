package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;

public class WorkActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private EditText searchBar;
    private RecyclerView jobRecyclerView;
    private ArrayList<WorkJobItem> jobList;
    private WorkJobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        // Hide the default ActionBar to remove the "Skills Hub" title bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize UI components
        searchBar = findViewById(R.id.search_bar_id);
        jobRecyclerView = findViewById(R.id.job_recycler_view);

        // Setup RecyclerView
        jobRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize job list
        createJobList();

        // Set up adapter
        jobAdapter = new WorkJobAdapter(this, jobList);
        jobRecyclerView.setAdapter(jobAdapter);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_work); // Ensure work tab is selected
    }

    private void createJobList() {
        jobList = new ArrayList<>();

        jobList.add(new WorkJobItem(
                "UI/UX Designer",
                "Create beautiful interfaces for mobile applications",
                4.7f,
                "Remote",
                "Design",
                30000,
                R.drawable.herosectioncourselearn
        ));

        jobList.add(new WorkJobItem(
                "Android Developer",
                "Develop native Android applications using Java/Kotlin",
                4.8f,
                "Part-time",
                "Tech",
                35000,
                R.drawable.herosectioncourselearn
        ));

        jobList.add(new WorkJobItem(
                "Content Writer",
                "Create engaging blog posts and articles for tech startups",
                4.5f,
                "Freelance",
                "Writing",
                15000,
                R.drawable.herosectioncourselearn
        ));

        jobList.add(new WorkJobItem(
                "Web Developer",
                "Build responsive websites using modern frameworks",
                4.6f,
                "Full-time",
                "Tech",
                40000,
                R.drawable.herosectioncourselearn
        ));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(WorkActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(WorkActivity.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_work) {
            return true; // We're already on the work page
        } else if (itemId == R.id.nav_notifications) {
            startActivity(new Intent(WorkActivity.this, NotificationsActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(WorkActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        }

        return false;
    }

    // Inner class for job item model
    public static class WorkJobItem {
        private String title;
        private String description;
        private float rating;
        private String tag1;
        private String tag2;
        private int price;
        private int imageResourceId;

        public WorkJobItem(String title, String description, float rating, String tag1, String tag2, int price, int imageResourceId) {
            this.title = title;
            this.description = description;
            this.rating = rating;
            this.tag1 = tag1;
            this.tag2 = tag2;
            this.price = price;
            this.imageResourceId = imageResourceId;
        }

        // Getters
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public float getRating() { return rating; }
        public String getTag1() { return tag1; }
        public String getTag2() { return tag2; }
        public int getPrice() { return price; }
        public int getImageResourceId() { return imageResourceId; }
    }
}