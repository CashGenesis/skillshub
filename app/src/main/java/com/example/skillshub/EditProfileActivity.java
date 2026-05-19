package com.example.skillshub;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private ImageView btnBack;
    private CircleImageView imgProfile;
    private EditText editName, editPhone, editExperience, editPortfolio;
    private Button btnSave;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize views
        initViews();

        // Load current user data
        loadUserData();

        // Set click listeners
        setClickListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        imgProfile = findViewById(R.id.img_profile);
        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        editExperience = findViewById(R.id.edit_experience);
        editPortfolio = findViewById(R.id.edit_portfolio);
        btnSave = findViewById(R.id.btn_save);
    }

    private void loadUserData() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String userId = currentUser.getUid();

        db.collection("users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("name");
                        String phone = documentSnapshot.getString("phone");
                        String experience = documentSnapshot.getString("experience");
                        String portfolio = documentSnapshot.getString("portfolio");

                        if (name != null) editName.setText(name);
                        if (phone != null) editPhone.setText(phone);
                        if (experience != null) editExperience.setText(experience);
                        if (portfolio != null) editPortfolio.setText(portfolio);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("EditProfile", "Error loading user data", e);
                    Toast.makeText(this, "Failed to load profile data", Toast.LENGTH_SHORT).show();
                });
    }

    private void setClickListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = editName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        String experience = editExperience.getText().toString().trim();
        String portfolio = editPortfolio.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }

        // Disable save button to prevent multiple clicks
        btnSave.setEnabled(false);

        String userId = currentUser.getUid();

        // Create update map
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("phone", phone);
        updates.put("experience", experience);
        updates.put("portfolio", portfolio);

        // Update Firestore
        db.collection("users").document(userId)
                .update(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("EditProfile", "Error updating profile", e);
                    Toast.makeText(this, "Failed to update profile: " + e.getMessage(), 
                            Toast.LENGTH_SHORT).show();
                    btnSave.setEnabled(true);
                });
    }
}
