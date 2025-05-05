package com.example.skillshub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CircleImageView imgProfile;
    private TextView txtName, txtEmail, txtPhone;
    private Button btnEditProfile, btnShareProfile;
    private Button btnFreelancer, btnRecruiter, btnLearner;
    private Button btnLogout;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the action bar/title bar to remove "Skillshub" text
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_profile);

        // Initialize views
        initViews();

        // Set user data
        loadUserData();

        // Set click listeners
        setClickListeners();

        // Setup BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile); // Ensure profile is selected
    }

    private void initViews() {
        // Profile information
        imgProfile = findViewById(R.id.imgProfile);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        // Profile action buttons
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnShareProfile = findViewById(R.id.btnShareProfile);

        // Role selection buttons
        btnFreelancer = findViewById(R.id.btnFreelancer);
        btnRecruiter = findViewById(R.id.btnRecruiter);
        btnLearner = findViewById(R.id.btnLearner);

        // Logout button
        btnLogout = findViewById(R.id.btnLogout);
    }

    private void loadUserData() {
        // Get the current user from Firebase Auth
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to Firestore
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            // Get user document from Firestore
            db.collection("users").document(userId)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            // Get data from document
                            String name = documentSnapshot.getString("name");
                            String email = documentSnapshot.getString("email");
                            String phone = documentSnapshot.getString("phone"); // This might be null if you don't store phone

                            // Update UI
                            txtName.setText(name);
                            txtEmail.setText("Email - " + email);

                            // Check if phone exists before setting it
                            if (phone != null && !phone.isEmpty()) {
                                txtPhone.setText("Mob No. - " + phone);
                            } else {
                                txtPhone.setText("Mob No. - Not provided");
                            }

                            // Show success message (optional)
                            Toast.makeText(this, "Profile loaded successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            // Document doesn't exist
                            Toast.makeText(this, "User profile not found", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle any errors
                        Log.e("Profile", "Error loading user data", e);
                        Toast.makeText(this, "Failed to load profile: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();

                        // Set default values in case of error
                        txtName.setText("Not available");
                        txtEmail.setText("Email - Not available");
                        txtPhone.setText("Mob No. - Not available");
                    });
        } else {
            // No user is signed in
            Toast.makeText(this, "No user signed in", Toast.LENGTH_SHORT).show();

            // Redirect to login page
            Intent intent = new Intent(this, LoginPage.class);
            startActivity(intent);
            finish();
        }


        // Set the default selected role
        setRoleButtonState(btnFreelancer);
    }

    private void setClickListeners() {
        btnEditProfile.setOnClickListener(v -> {
            // TODO: Navigate to edit profile screen
            Toast.makeText(this, "Edit Profile clicked", Toast.LENGTH_SHORT).show();
        });

        btnShareProfile.setOnClickListener(v -> {
            // Share profile functionality
            shareProfile();
        });

        // Role selection buttons
        btnFreelancer.setOnClickListener(v -> {
            setRoleButtonState(btnFreelancer);
        });

        btnRecruiter.setOnClickListener(v -> {
            setRoleButtonState(btnRecruiter);
        });

        btnLearner.setOnClickListener(v -> {
            setRoleButtonState(btnLearner);
        });

        btnLogout.setOnClickListener(v -> {
            logout();
        });
    }

    private void setRoleButtonState(Button selectedButton) {
        // Reset all buttons to default state
        btnFreelancer.setBackgroundResource(R.drawable.button_outline_bg);
        btnFreelancer.setTextColor(getResources().getColor(R.color.white));

        btnRecruiter.setBackgroundResource(R.drawable.button_outline_bg);
        btnRecruiter.setTextColor(getResources().getColor(R.color.white));

        btnLearner.setBackgroundResource(R.drawable.button_outline_bg);
        btnLearner.setTextColor(getResources().getColor(R.color.white));

        // Set selected button state
        selectedButton.setBackgroundResource(R.drawable.button_yellow_bg);
        selectedButton.setTextColor(getResources().getColor(R.color.black));

        // Save selected role to preferences
        String selectedRole = "";
        if (selectedButton == btnFreelancer) {
            selectedRole = "Freelancer";
        } else if (selectedButton == btnRecruiter) {
            selectedRole = "Recruiter";
        } else if (selectedButton == btnLearner) {
            selectedRole = "Learner";
        }

        saveSelectedRole(selectedRole);
    }

    private void saveSelectedRole(String role) {
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selectedRole", role);
        editor.apply();
    }

    private void shareProfile() {
        // Create a share intent
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out my Skills Hub profile");

        // Customize this message as needed
        String shareMessage = "Hi! Check out my profile on Skills Hub app.\n\n" +
                "Name: " + txtName.getText().toString() + "\n" +
                "Email: " + txtEmail.getText().toString().replace("Email - ", "") + "\n" +
                "Download Skills Hub app to connect with me!";

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private void logout() {
        // Sign out from Firebase Auth
        FirebaseAuth.getInstance().signOut();

        // Clear user session (SharedPreferences)
        SharedPreferences preferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // Navigate to login screen
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, LoginPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(ProfileActivity.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_work) {
            startActivity(new Intent(ProfileActivity.this, WorkActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_notifications) {
            startActivity(new Intent(ProfileActivity.this, NotificationsActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_profile) {
            // Already on profile
            return true;
        }

        return false;
    }
}