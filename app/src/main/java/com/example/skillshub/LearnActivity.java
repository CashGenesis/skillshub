package com.example.skillshub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class LearnActivity extends AppCompatActivity {

    private LinearLayout skillBoxContainer;
    private List<SkillItem> skillItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        // Bottom Navigation setup
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_ideas);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });

        // Skill box setup
        skillBoxContainer = findViewById(R.id.skillBoxContainer);
        initializeSkillData();
        displaySkillBoxes();
    }

    private void initializeSkillData() {
        skillItems = new ArrayList<>();

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

        SkillItem webDev = new SkillItem(
                "Web Development",
                "Master HTML, CSS, JavaScript and modern frameworks to build responsive, dynamic websites from scratch.",
                R.drawable.graphic_designing
        );
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        webDev.addAppIcon(R.drawable.photoshop_icon);
        skillItems.add(webDev);

        SkillItem mobileDev = new SkillItem(
                "Mobile Development",
                "Learn to design and build native mobile applications for Android and iOS platforms using modern frameworks.",
                R.drawable.graphic_designing
        );
        mobileDev.addAppIcon(R.drawable.photoshop_icon);
        mobileDev.addAppIcon(R.drawable.photoshop_icon);
        mobileDev.addAppIcon(R.drawable.canva_logo);
        mobileDev.addAppIcon(R.drawable.canva_logo);
        skillItems.add(mobileDev);

        SkillItem uiUxDesign = new SkillItem(
                "UI/UX Design",
                "Learn user-centered design principles to create intuitive interfaces and engaging digital experiences for websites and applications.",
                R.drawable.graphic_designing
        );
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        uiUxDesign.addAppIcon(R.drawable.canva_logo);
        skillItems.add(uiUxDesign);

        SkillItem dataScience = new SkillItem(
                "Data Science",
                "Develop skills in data analysis, machine learning, and statistical modeling to extract valuable insights from complex datasets.",
                R.drawable.graphic_designing
        );
        dataScience.addAppIcon(R.drawable.canva_logo);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        dataScience.addAppIcon(R.drawable.illustrator_icon);
        skillItems.add(dataScience);

        SkillItem digitalMarketing = new SkillItem(
                "Digital Marketing",
                "Master SEO, social media marketing, content strategies, and paid advertising to reach audiences and drive business growth.",
                R.drawable.graphic_designing
        );
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        digitalMarketing.addAppIcon(R.drawable.illustrator_icon);
        skillItems.add(digitalMarketing);
    }

    private void displaySkillBoxes() {
        skillBoxContainer.removeAllViews();

        for (SkillItem skill : skillItems) {
            addSkillBox(skill);
        }
    }

    private void addSkillBox(SkillItem skill) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View skillBoxView = inflater.inflate(R.layout.skill_box_item, skillBoxContainer, false);

        // Apply margin between skill boxes
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, dpToPx(16)); // Bottom margin = 16dp
        skillBoxView.setLayoutParams(layoutParams);

        ImageView illustrationImageView = skillBoxView.findViewById(R.id.skillIllustration);
        TextView titleTextView = skillBoxView.findViewById(R.id.skillTitle);
        TextView descriptionTextView = skillBoxView.findViewById(R.id.skillDescription);
        LinearLayout appIconsContainer = skillBoxView.findViewById(R.id.appIconsContainer);

        illustrationImageView.setImageResource(skill.getIllustrationResourceId());
        titleTextView.setText(skill.getTitle());
        descriptionTextView.setText(skill.getDescription());

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

        skillBoxContainer.addView(skillBoxView);
    }

    public void addNewSkill(String title, String description, int illustrationResourceId, List<Integer> appIconIds) {
        SkillItem newSkill = new SkillItem(title, description, illustrationResourceId);
        for (Integer iconId : appIconIds) {
            newSkill.addAppIcon(iconId);
        }
        skillItems.add(newSkill);
        addSkillBox(newSkill);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
