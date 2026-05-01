package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchConfirmationActivity extends AppCompatActivity {

    private ImageView btnClose;
    private TextView txtSubtitle, txtMatchScore, txtYouTeach, txtTheyTeach;
    private CircleImageView imgUserProfile, imgMatchProfile;
    private Button btnSendMessage, btnKeepSwiping;

    private String matchName;
    private String matchSkill;
    private String userSkill;
    private int matchScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_match_confirmation);

        // Initialize views
        btnClose = findViewById(R.id.btn_close);
        txtSubtitle = findViewById(R.id.txt_subtitle);
        txtMatchScore = findViewById(R.id.txt_match_score);
        txtYouTeach = findViewById(R.id.txt_you_teach);
        txtTheyTeach = findViewById(R.id.txt_they_teach);
        imgUserProfile = findViewById(R.id.img_user_profile);
        imgMatchProfile = findViewById(R.id.img_match_profile);
        btnSendMessage = findViewById(R.id.btn_send_message);
        btnKeepSwiping = findViewById(R.id.btn_keep_swiping);

        // Get data from intent
        matchName = getIntent().getStringExtra("match_name");
        matchSkill = getIntent().getStringExtra("match_skill");
        userSkill = getIntent().getStringExtra("user_skill");
        matchScore = getIntent().getIntExtra("match_score", 94);

        if (matchName == null) matchName = "Alex Rivera";
        if (matchSkill == null) matchSkill = "Video Editing";
        if (userSkill == null) userSkill = "UI Design";

        // Set data
        txtSubtitle.setText("You and " + matchName + " have matched!\nStart exchanging skills now.");
        txtMatchScore.setText(matchScore + "%");
        txtYouTeach.setText(userSkill);
        txtTheyTeach.setText(matchSkill);

        // Close button
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Send Message button
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MatchConfirmationActivity.this, 
                    "Opening chat with " + matchName, Toast.LENGTH_SHORT).show();
                // TODO: Navigate to chat activity
                finish();
            }
        });

        // Keep Swiping button
        btnKeepSwiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
