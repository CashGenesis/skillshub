package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ViewAllMatchesActivity extends AppCompatActivity {

    private RecyclerView matchesRecyclerView;
    private ImageView btnBack;
    private List<MatchItem> matchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_view_all_matches);

        // Initialize views
        matchesRecyclerView = findViewById(R.id.matches_recycler_view);
        btnBack = findViewById(R.id.btn_back);

        // Setup RecyclerView
        matchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize match data
        initializeMatchData();

        // Back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initializeMatchData() {
        matchList = new ArrayList<>();

        // Add sample match data
        matchList.add(new MatchItem(
                "Alex Rivera",
                "Video Editing",
                "Mastering cinematic storytelling through advanced editing techniques",
                4.9f,
                "After Effects",
                "Blender 3D",
                R.drawable.a
        ));

        matchList.add(new MatchItem(
                "Sarah Johnson",
                "Graphic Design",
                "Creating stunning visual identities and brand experiences",
                4.8f,
                "Photoshop",
                "Illustrator",
                R.drawable.b
        ));

        matchList.add(new MatchItem(
                "Michael Chen",
                "Web Development",
                "Building responsive and modern web applications",
                4.7f,
                "React",
                "Node.js",
                R.drawable.c
        ));

        matchList.add(new MatchItem(
                "Emma Williams",
                "UI/UX Design",
                "Designing intuitive user experiences for digital products",
                4.9f,
                "Figma",
                "Adobe XD",
                R.drawable.profilepic
        ));

        matchList.add(new MatchItem(
                "David Martinez",
                "Content Writing",
                "Crafting compelling stories and engaging content",
                4.6f,
                "SEO Writing",
                "Copywriting",
                R.drawable.c
        ));

        matchList.add(new MatchItem(
                "Lisa Anderson",
                "Digital Marketing",
                "Growing brands through strategic digital campaigns",
                4.8f,
                "Social Media",
                "Analytics",
                R.drawable.a
        ));

        // TODO: Set up adapter
        ViewAllMatchesAdapter adapter = new ViewAllMatchesAdapter(this, matchList);
        matchesRecyclerView.setAdapter(adapter);
    }

    // Inner class for match item model
    public static class MatchItem {
        private String name;
        private String skillCategory;
        private String description;
        private float rating;
        private String teaches;
        private String wantsToLearn;
        private int imageResourceId;

        public MatchItem(String name, String skillCategory, String description, float rating, 
                        String teaches, String wantsToLearn, int imageResourceId) {
            this.name = name;
            this.skillCategory = skillCategory;
            this.description = description;
            this.rating = rating;
            this.teaches = teaches;
            this.wantsToLearn = wantsToLearn;
            this.imageResourceId = imageResourceId;
        }

        // Getters
        public String getName() { return name; }
        public String getSkillCategory() { return skillCategory; }
        public String getDescription() { return description; }
        public float getRating() { return rating; }
        public String getTeaches() { return teaches; }
        public String getWantsToLearn() { return wantsToLearn; }
        public int getImageResourceId() { return imageResourceId; }
    }
}
