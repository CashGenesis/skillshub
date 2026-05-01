package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import java.util.ArrayList;
import java.util.List;

public class SkillYouKnowActivity extends AppCompatActivity {

    private LinearLayout skillsContainer;
    private Button btnNext;
    private List<String> selectedSkills = new ArrayList<>();
    private String[] skills = {
            "Video Editing",
            "Graphic Designing",
            "Web Dev",
            "Content Writing",
            "App Development",
            "UI/UX Design",
            "Digital Marketing",
            "Data Science"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_skill_you_know);

        skillsContainer = findViewById(R.id.skills_container);
        btnNext = findViewById(R.id.btn_next);

        // Create skill pills
        createSkillPills();

        // Set up title with colored "barter" text
        TextView txtTitle = findViewById(R.id.txt_title);
        String titleText = "Curate your barter identity.";
        android.text.SpannableString spannableTitle = new android.text.SpannableString(titleText);
        int startIndex = titleText.indexOf("barter");
        int endIndex = startIndex + "barter".length();
        spannableTitle.setSpan(
                new android.text.style.ForegroundColorSpan(Color.parseColor("#C8FF00")),
                startIndex,
                endIndex,
                android.text.Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        txtTitle.setText(spannableTitle);

        // Next button click
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedSkills.isEmpty()) {
                    Intent intent = new Intent(SkillYouKnowActivity.this, SkillWantLearnActivity.class);
                    intent.putStringArrayListExtra("skills_you_know", (ArrayList<String>) selectedSkills);
                    intent.putExtra("USER_ID", getIntent().getStringExtra("USER_ID"));
                    startActivity(intent);
                }
            }
        });
    }

    private void createSkillPills() {
        for (String skill : skills) {
            // Create pill button
            TextView pillButton = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 12);
            pillButton.setLayoutParams(params);
            
            pillButton.setText(skill);
            pillButton.setTextSize(16);
            pillButton.setTextColor(Color.parseColor("#666666"));
            pillButton.setPadding(48, 24, 48, 24);
            
            // Create rounded background
            GradientDrawable background = new GradientDrawable();
            background.setShape(GradientDrawable.RECTANGLE);
            background.setCornerRadius(100);
            background.setColor(Color.WHITE);
            background.setStroke(2, Color.parseColor("#DDDDDD"));
            pillButton.setBackground(background);

            // Click listener
            pillButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GradientDrawable bg = new GradientDrawable();
                    bg.setShape(GradientDrawable.RECTANGLE);
                    bg.setCornerRadius(100);
                    
                    if (selectedSkills.contains(skill)) {
                        // Deselect
                        selectedSkills.remove(skill);
                        bg.setColor(Color.WHITE);
                        bg.setStroke(2, Color.parseColor("#DDDDDD"));
                        pillButton.setTextColor(Color.parseColor("#666666"));
                    } else {
                        // Select
                        selectedSkills.add(skill);
                        bg.setColor(Color.parseColor("#1A1A1A"));
                        bg.setStroke(0, Color.TRANSPARENT);
                        pillButton.setTextColor(Color.parseColor("#C8FF00"));
                    }
                    pillButton.setBackground(bg);

                    // Enable/disable next button
                    btnNext.setEnabled(!selectedSkills.isEmpty());
                    btnNext.setAlpha(selectedSkills.isEmpty() ? 0.5f : 1.0f);
                }
            });

            skillsContainer.addView(pillButton);
        }
    }
}
