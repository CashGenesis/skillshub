package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    private ArrayList<WorkJobItem> filteredJobList;
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
        filteredJobList = new ArrayList<>(jobList); // Initialize with all items

        // Set up adapter
        jobAdapter = new WorkJobAdapter(this, filteredJobList);
        jobRecyclerView.setAdapter(jobAdapter);

        // Set up search functionality
        setupSearchBar();

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_work); // Ensure work tab is selected
    }

    private void setupSearchBar() {
        if (searchBar != null) {
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not needed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Filter jobs when text changes
                    filterJobs(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Not needed
                }
            });
        }
    }

    private void filterJobs(String query) {
        filteredJobList.clear();

        if (query.isEmpty()) {
            // If search is empty, show all jobs
            filteredJobList.addAll(jobList);
        } else {
            // Filter based on job title, description, and tags
            String lowerCaseQuery = query.toLowerCase();
            for (WorkJobItem job : jobList) {
                if (job.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        job.getDescription().toLowerCase().contains(lowerCaseQuery) ||
                        job.getTag1().toLowerCase().contains(lowerCaseQuery) ||
                        job.getTag2().toLowerCase().contains(lowerCaseQuery)) {
                    filteredJobList.add(job);
                }
            }
        }

        // Notify adapter of data change
        jobAdapter.notifyDataSetChanged();
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

    // Method to add a new job programmatically
    public void addNewJob(String title, String description, float rating, String tag1, String tag2, int price, int imageResourceId) {
        WorkJobItem newJob = new WorkJobItem(title, description, rating, tag1, tag2, price, imageResourceId);
        jobList.add(newJob);

        // Also add to filtered list if it matches current search criteria
        String searchQuery = searchBar.getText().toString().toLowerCase();
        if (searchQuery.isEmpty() ||
                title.toLowerCase().contains(searchQuery) ||
                description.toLowerCase().contains(searchQuery) ||
                tag1.toLowerCase().contains(searchQuery) ||
                tag2.toLowerCase().contains(searchQuery)) {
            filteredJobList.add(newJob);
        }

        jobAdapter.notifyDataSetChanged();
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