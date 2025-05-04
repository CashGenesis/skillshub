package com.example.skillshub;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        // Hide the default ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get job details from intent
        String title = getIntent().getStringExtra("JOB_TITLE");
        String description = getIntent().getStringExtra("JOB_DESCRIPTION");
        float rating = getIntent().getFloatExtra("JOB_RATING", 0f);
        String tag1 = getIntent().getStringExtra("JOB_TAG1");
        String tag2 = getIntent().getStringExtra("JOB_TAG2");
        int price = getIntent().getIntExtra("JOB_PRICE", 0);
        int imageResourceId = getIntent().getIntExtra("JOB_IMAGE", 0);

        // Initialize and set views
        ImageView jobImage = findViewById(R.id.detail_job_image);
        TextView jobTitle = findViewById(R.id.detail_job_title);
        TextView jobDescription = findViewById(R.id.detail_job_description);
        TextView jobRating = findViewById(R.id.detail_job_rating);
        TextView jobTag1 = findViewById(R.id.detail_job_tag1);
        TextView jobTag2 = findViewById(R.id.detail_job_tag2);
        TextView jobPrice = findViewById(R.id.detail_job_price);
        Button applyButton = findViewById(R.id.apply_button);

        jobImage.setImageResource(imageResourceId);
        jobTitle.setText(title);
        jobDescription.setText(description);
        jobRating.setText(String.valueOf(rating));
        jobTag1.setText(tag1);
        jobTag2.setText(tag2);
        jobPrice.setText("₹ " + price);

        // Back button functionality
        ImageView backButton = findViewById(R.id.back_button);
        if (backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        // Apply button functionality
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobDetailActivity.this, "Application submitted for " + title, Toast.LENGTH_SHORT).show();
                // You can add more functionality here like opening a form or sending data to server
            }
        });

        // Add additional details based on job position/category if needed
        // For example, expand the job description with more details specific to the job
        String expandedDescription = description;

        // Add more details based on job title
        if (title.contains("Designer")) {
            expandedDescription += "\n\nRequirements:\n" +
                    "• 1-3 years of experience in UI/UX design\n" +
                    "• Proficiency in Figma, Adobe XD, or Sketch\n" +
                    "• Understanding of user-centered design principles\n" +
                    "• Portfolio showcasing previous work\n\n" +
                    "This position offers flexibility to work remotely with occasional team meetings.";
        } else if (title.contains("Developer")) {
            expandedDescription += "\n\nRequirements:\n" +
                    "• Experience with Java/Kotlin for Android development\n" +
                    "• Understanding of Android SDK and lifecycle\n" +
                    "• Knowledge of RESTful APIs and JSON\n" +
                    "• Experience with Git version control\n\n" +
                    "This role involves creating robust mobile applications for various clients.";
        } else if (title.contains("Writer")) {
            expandedDescription += "\n\nRequirements:\n" +
                    "• Excellent written English skills\n" +
                    "• Understanding of SEO principles\n" +
                    "• Ability to research technical topics\n" +
                    "• Experience writing for digital platforms\n\n" +
                    "This is a freelance position with flexible hours and deadlines.";
        } else {
            expandedDescription += "\n\nRequirements:\n" +
                    "• Relevant education or experience in the field\n" +
                    "• Strong communication skills\n" +
                    "• Ability to work independently and meet deadlines\n" +
                    "• Passion for innovation and problem-solving\n\n" +
                    "This position offers competitive compensation and opportunities for growth.";
        }

        jobDescription.setText(expandedDescription);
    }
}