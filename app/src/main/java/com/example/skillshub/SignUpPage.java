package com.example.skillshub;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {

    // UI components
    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirm;
    private Button btnContinue;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private static final String TAG = "SignUpPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        // Hide action bar if present
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirm = findViewById(R.id.editTextConfirm);
        btnContinue = findViewById(R.id.btnContinue);

        // Set click listener for continue button
        btnContinue.setOnClickListener(v -> {
            // Validate inputs and register user
            registerUser();
        });

        setupBackgroundVideo();
    }

    private void setupBackgroundVideo() {
        VideoView videoView = findViewById(R.id.backgroundVideo);

        // Set video URI from raw folder
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login_vid);
        videoView.setVideoURI(videoUri);

        // Configure video behavior
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(true); // Loop video
            mp.setVolume(0f, 0f); // Mute video

            // Stretch video to fit screen and crop if needed
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        });

        videoView.start();
    }

    private void registerUser() {
        // Get input values
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirm.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            editTextConfirm.setError("Passwords don't match");
            editTextConfirm.requestFocus();
            return;
        }

        // Disable button to prevent multiple clicks
        btnContinue.setEnabled(false);

        // Show a toast to indicate registration is in progress
        Toast.makeText(SignUpPage.this, "Creating account...", Toast.LENGTH_SHORT).show();

        // Create user with Firebase Auth
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Save user data to Firestore
                        saveUserToFirestore(user.getUid(), name, email);
                    } else {
                        // If sign up fails, display a message to the user
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        btnContinue.setEnabled(true);
                        Toast.makeText(SignUpPage.this, "Registration failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserToFirestore(String userId, String name, String email) {
        // Create a user data map
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("userId", userId);
        userData.put("createdAt", System.currentTimeMillis());

        // Add user to Firestore
        db.collection("users").document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "User data saved to Firestore");
                    Toast.makeText(SignUpPage.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to main activity
                    Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                    intent.putExtra("USER_ID", userId);
                    startActivity(intent);
                    finish(); // Close signup activity
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error adding user data", e);
                    btnContinue.setEnabled(true);
                    Toast.makeText(SignUpPage.this, "Failed to save user data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, redirect to main activity
            Intent intent = new Intent(SignUpPage.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}