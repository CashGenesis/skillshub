package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private LinearLayout skillBoxContainer;
    private List<SkillItem> skillItems;
    private List<SkillItem> filteredSkillItems;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // Setup search bar
        searchEditText = findViewById(R.id.search_bar_id);
        setupSearchBar();

        // Bottom Navigation setup
        setupBottomNavigation();

        // Skill box setup
        skillBoxContainer = findViewById(R.id.skillBoxContainer);
        initializeSkillData();
        filteredSkillItems = new ArrayList<>(skillItems); // Initialize with all items
        displaySkillBoxes();
    }

    private void setupSearchBar() {
        if (searchEditText != null) {
            // Make the search bar clickable and focusable
            searchEditText.setFocusable(true);
            searchEditText.setClickable(true);

            // Add text change listener
            searchEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Not needed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Filter skills when text changes
                    filterSkills(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Not needed
                }
            });
        }
    }

    private void filterSkills(String query) {
        filteredSkillItems.clear();

        if (query.isEmpty()) {
            // If search is empty, show all skills
            filteredSkillItems.addAll(skillItems);
        } else {
            // Filter based on skill title and description
            String lowerCaseQuery = query.toLowerCase();
            for (SkillItem skill : skillItems) {
                if (skill.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                        skill.getDescription().toLowerCase().contains(lowerCaseQuery)) {
                    filteredSkillItems.add(skill);
                }
            }
        }

        // Display the filtered results
        displaySkillBoxes();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_ideas);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                startActivity(new Intent(LearnActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_ideas) {
                return true;
            } else if (itemId == R.id.nav_work) {
                startActivity(new Intent(LearnActivity.this, WorkActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_notifications) {
                startActivity(new Intent(LearnActivity.this, NotificationsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(LearnActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    private void initializeSkillData() {
        skillItems = new ArrayList<>();

        // Graphic Design
        SkillItem graphicDesign = new SkillItem(
                "Graphic Designing",
                "Learn graphic design fundamentals, master core skills like typography, color theory, and image manipulation for creative and professional opportunities.",
                R.drawable.graphic_designing
        );
        graphicDesign.addAppIcon(R.drawable.photoshop_icon);
        graphicDesign.addAppIcon(R.drawable.illustrator_icon);
        graphicDesign.addAppIcon(R.drawable.figma_logo);
        graphicDesign.addAppIcon(R.drawable.canva_logo);
        skillItems.add(graphicDesign);

        // Web Development
        SkillItem webDev = new SkillItem(
                "Web Development",
                "Master HTML, CSS, JavaScript and modern frameworks to build responsive, dynamic websites from scratch.",
                R.drawable.graphic_designing  // Consider using a specific web dev illustration
        );
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        skillItems.add(webDev);

        // Mobile Development
        SkillItem mobileDev = new SkillItem(
                "Mobile Development",
                "Learn to design and build native mobile applications for Android and iOS platforms using modern frameworks.",
                R.drawable.graphic_designing  // Consider using a specific mobile dev illustration
        );
        mobileDev.addAppIcon(R.drawable.photoshop_icon);
        mobileDev.addAppIcon(R.drawable.photoshop_icon);
        mobileDev.addAppIcon(R.drawable.canva_logo);
        mobileDev.addAppIcon(R.drawable.canva_logo);
        skillItems.add(mobileDev);

        // UI/UX Design
        SkillItem uiUxDesign = new SkillItem(
                "UI/UX Design",
                "Learn user-centered design principles to create intuitive interfaces and engaging digital experiences for websites and applications.",
                R.drawable.graphic_designing  // Consider using a specific UI/UX illustration
        );
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        skillItems.add(uiUxDesign);

        // Data Science
        SkillItem dataScience = new SkillItem(
                "Data Science",
                "Develop skills in data analysis, machine learning, and statistical modeling to extract valuable insights from complex datasets.",
                R.drawable.graphic_designing  // Consider using a specific data science illustration
        );
        dataScience.addAppIcon(R.drawable.canva_logo);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        skillItems.add(dataScience);

        // Digital Marketing
        SkillItem digitalMarketing = new SkillItem(
                "Digital Marketing",
                "Master SEO, social media marketing, content strategies, and paid advertising to reach audiences and drive business growth.",
                R.drawable.graphic_designing  // Consider using a specific digital marketing illustration
        );
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        skillItems.add(digitalMarketing);
    }

    private void displaySkillBoxes() {
        skillBoxContainer.removeAllViews();

        if (filteredSkillItems.isEmpty()) {
            // Display a "No results found" message
            TextView noResultsTextView = new TextView(this);
            noResultsTextView.setText("No skills found matching your search");
            noResultsTextView.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
            noResultsTextView.setTextSize(16);
            skillBoxContainer.addView(noResultsTextView);
        } else {
            // Display filtered skills
            for (SkillItem skill : filteredSkillItems) {
                addSkillBox(skill);
            }
        }
    }

    private void addSkillBox(SkillItem skill) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View skillBoxView = inflater.inflate(R.layout.skill_box_item, skillBoxContainer, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, dpToPx(16));
        skillBoxView.setLayoutParams(layoutParams);

        // Set up views within the skill box
        ImageView illustrationImageView = skillBoxView.findViewById(R.id.skillIllustration);
        TextView titleTextView = skillBoxView.findViewById(R.id.skillTitle);
        TextView descriptionTextView = skillBoxView.findViewById(R.id.skillDescription);
        LinearLayout appIconsContainer = skillBoxView.findViewById(R.id.appIconsContainer);

        // Set content
        illustrationImageView.setImageResource(skill.getIllustrationResourceId());
        titleTextView.setText(skill.getTitle());
        descriptionTextView.setText(skill.getDescription());

        // Add app icons
        appIconsContainer.removeAllViews();
        for (Integer iconId : skill.getAppIconResourceIds()) {
            ImageView iconView = new ImageView(this);
            LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(
                    dpToPx(24), dpToPx(24)
            );
            iconParams.setMarginEnd(dpToPx(8));
            iconView.setLayoutParams(iconParams);
            iconView.setImageResource(iconId);
            appIconsContainer.addView(iconView);
        }

        // Make the whole box clickable and redirect to a single common activity
        skillBoxView.setOnClickListener(v -> {
            // Create intent for the common activity - all boxes go to the same activity
            Intent intent = new Intent(LearnActivity.this, CourseLearn.class);

            // Don't pass the skill title to avoid changing the title in the target activity
            // We'll use a fixed title in CourseLearn.java instead

            // If you need to know which box was clicked (for analytics or other purposes)
            // you can pass a fixed identifier instead
            intent.putExtra("source", "skills_hub");

            // Start the activity
            startActivity(intent);
        });

        skillBoxContainer.addView(skillBoxView);
    }

    /**
     * Add a new skill programmatically
     */
    public void addNewSkill(String title, String description, int illustrationResourceId, List<Integer> appIconIds) {
        SkillItem newSkill = new SkillItem(title, description, illustrationResourceId);
        for (Integer iconId : appIconIds) {
            newSkill.addAppIcon(iconId);
        }
        skillItems.add(newSkill);
        filteredSkillItems.add(newSkill);
        addSkillBox(newSkill);
    }

    /**
     * Convert dp to pixels
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}