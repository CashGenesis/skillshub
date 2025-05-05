package com.example.skillshub;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginPage extends AppCompatActivity {

    private static final String TAG = "LoginPage";

    // UI elements
    private EditText editTextEmail, editTextPassword, editTextName;
    private Button btnContinue, btnSignUp;

    // Firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Firebase instances
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        btnContinue = findViewById(R.id.btnContinue);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Login button click listener
        btnContinue.setOnClickListener(v -> {
            loginUser();
        });

        // Sign up button click listener
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginPage.this, SignUpPage.class);
            startActivity(intent);
        });

        // Setup background video
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

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        // Show some loading indicator if needed
        // For example, disable the button and show progress
        btnContinue.setEnabled(false);

        // Authenticate with Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, get user data from Firestore
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            fetchUserDataAndProceed(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            btnContinue.setEnabled(true);

                            // Handle specific authentication errors
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(LoginPage.this, "User does not exist. Please sign up.",
                                        Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(LoginPage.this, "Invalid password. Please try again.",
                                        Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(LoginPage.this, "Authentication failed: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void fetchUserDataAndProceed(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            // Get user data from Firestore
            DocumentReference userRef = db.collection("users").document(userId);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    btnContinue.setEnabled(true);

                    if (documentSnapshot.exists()) {
                        // Document exists, retrieve user data
                        String name = documentSnapshot.getString("name");
                        // You can retrieve other user data as needed

                        Toast.makeText(LoginPage.this, "Welcome back, " + name, Toast.LENGTH_SHORT).show();

                        // Navigate to main activity
                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        // You can pass user data to MainActivity if needed
                        intent.putExtra("USER_ID", userId);
                        intent.putExtra("USER_NAME", name);
                        startActivity(intent);
                        finish(); // Close the login activity
                    } else {
                        // This should rarely happen - user exists in Auth but not in Firestore
                        Log.d(TAG, "User document does not exist in Firestore");
                        Toast.makeText(LoginPage.this, "User profile not found. Please contact support.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    btnContinue.setEnabled(true);
                    Log.e(TAG, "Error fetching user data", e);
                    Toast.makeText(LoginPage.this, "Error fetching user data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}