package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // Disable the button
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
                        Boolean onboardingCompleted = documentSnapshot.getBoolean("onboardingCompleted");

                        Toast.makeText(LoginPage.this, "Welcome back, " + name, Toast.LENGTH_SHORT).show();

                        // Check if onboarding is completed
                        if (onboardingCompleted != null && onboardingCompleted) {
                            // Onboarding completed, go to MainActivity
                            Intent intent = new Intent(LoginPage.this, MainActivity.class);
                            intent.putExtra("USER_ID", userId);
                            intent.putExtra("USER_NAME", name);
                            startActivity(intent);
                        } else {
                            // Onboarding not completed, go to SkillYouKnowActivity
                            Intent intent = new Intent(LoginPage.this, SkillYouKnowActivity.class);
                            intent.putExtra("USER_ID", userId);
                            startActivity(intent);
                        }
                        finish(); // Close the login activity
                    } else {
                        // User exists in Auth but not in Firestore, start onboarding
                        Log.d(TAG, "User document does not exist in Firestore, starting onboarding");
                        Intent intent = new Intent(LoginPage.this, SkillYouKnowActivity.class);
                        intent.putExtra("USER_ID", userId);
                        startActivity(intent);
                        finish();
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