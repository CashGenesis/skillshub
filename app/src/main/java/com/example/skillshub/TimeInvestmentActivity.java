package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class TimeInvestmentActivity extends AppCompatActivity {

    private static final String TAG = "TimeInvestment";
    
    private SeekBar timeSlider;
    private TextView txtHours;
    private TextView txtTitle;
    private Button btnNext;
    private int selectedHours = 12;
    
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_time_investment);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        timeSlider = findViewById(R.id.time_slider);
        txtHours = findViewById(R.id.txt_hours);
        txtTitle = findViewById(R.id.txt_title);
        btnNext = findViewById(R.id.btn_next);

        // Get data from previous screens
        ArrayList<String> skillsYouKnow = getIntent().getStringArrayListExtra("skills_you_know");
        ArrayList<String> skillsWantLearn = getIntent().getStringArrayListExtra("skills_want_learn");
        String userId = getIntent().getStringExtra("USER_ID");

        // Set up title with colored "barter" text
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

        // Set up slider
        timeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Map progress (0-38) to hours (2-40)
                selectedHours = progress + 2;
                txtHours.setText(String.valueOf(selectedHours));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Just update the value, don't navigate
            }
        });

        // Next button click
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save onboarding data to Firebase
                saveOnboardingData(userId, skillsYouKnow, skillsWantLearn, selectedHours);
            }
        });
    }

    private void saveOnboardingData(String userId, ArrayList<String> skillsYouKnow, 
                                    ArrayList<String> skillsWantLearn, int weeklyHours) {
        // Disable button to prevent multiple clicks
        btnNext.setEnabled(false);
        Toast.makeText(this, "Saving your preferences...", Toast.LENGTH_SHORT).show();

        // Get current user ID if not passed
        if (userId == null && mAuth.getCurrentUser() != null) {
            userId = mAuth.getCurrentUser().getUid();
        }

        if (userId == null) {
            Log.e(TAG, "User ID is null, cannot save data");
            Toast.makeText(this, "Error: User not logged in. Please restart the app.", Toast.LENGTH_LONG).show();
            btnNext.setEnabled(true);
            return;
        }

        Log.d(TAG, "Saving data for user: " + userId);

        // Create update map
        Map<String, Object> updates = new HashMap<>();
        updates.put("skillsYouKnow", skillsYouKnow);
        updates.put("skillsWantToLearn", skillsWantLearn);
        updates.put("weeklyTimeInvestment", weeklyHours);
        updates.put("onboardingCompleted", true);
        updates.put("onboardingCompletedAt", System.currentTimeMillis());

        // Use set with merge option instead of update (creates document if it doesn't exist)
        db.collection("users").document(userId)
                .set(updates, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Onboarding data saved successfully");
                    Toast.makeText(TimeInvestmentActivity.this, "Profile completed!", Toast.LENGTH_SHORT).show();

                    // Navigate to MainActivity
                    Intent intent = new Intent(TimeInvestmentActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error saving onboarding data: " + e.getMessage(), e);
                    Toast.makeText(TimeInvestmentActivity.this, 
                        "Error: " + e.getMessage() + ". Check your internet connection.", 
                        Toast.LENGTH_LONG).show();
                    btnNext.setEnabled(true);
                });
    }
}
