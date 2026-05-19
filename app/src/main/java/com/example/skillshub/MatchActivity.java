package com.example.skillshub;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private CardView btnAccept, btnReject;
    private FrameLayout cardContainer;
    private TextView txtViewAll;
    private List<MatchCard> matchCards;
    private int currentCardIndex = 0;
    private View currentCardView;
    
    private float initialX, initialY;
    private static final float SWIPE_THRESHOLD = 300f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide the default ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_match);

        // Initialize views
        btnAccept = findViewById(R.id.btn_accept);
        btnReject = findViewById(R.id.btn_reject);
        cardContainer = findViewById(R.id.card_container);
        txtViewAll = findViewById(R.id.txt_view_all);

        // Setup bottom navigation
        setupBottomNavigation();

        // Initialize match cards data
        initializeMatchCards();

        // Show first card
        showCard(currentCardIndex);

        // View All click listener
        txtViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchActivity.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });

        // Accept button click
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptCard();
            }
        });

        // Reject button click
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectCard();
            }
        });
    }

    private void initializeMatchCards() {
        matchCards = new ArrayList<>();
        
        matchCards.add(new MatchCard(
                "Video Editing",
                "Mastering cinematic storytelling through advanced editing techniques and visual rhythm",
                "Alex Rivera",
                "LUMINA STUDIO ORG",
                4.9f,
                "After Effects",
                "Blender 3D",
                R.drawable.graphdesign,
                R.drawable.a
        ));

        matchCards.add(new MatchCard(
                "Graphic Design",
                "Creating stunning visual identities and brand experiences with modern design tools",
                "Sarah Chen",
                "CREATIVE MINDS CO",
                4.8f,
                "Photoshop",
                "UI/UX Design",
                R.drawable.graphdesign,
                R.drawable.b
        ));

        matchCards.add(new MatchCard(
                "Web Development",
                "Building responsive and dynamic web applications using modern frameworks",
                "Mike Johnson",
                "CODE FACTORY",
                4.7f,
                "React",
                "Node.js",
                R.drawable.graphdesign,
                R.drawable.c
        ));

        matchCards.add(new MatchCard(
                "Content Writing",
                "Crafting compelling narratives and SEO-optimized content for digital platforms",
                "Emma Williams",
                "WORD SMITH STUDIO",
                4.6f,
                "Copywriting",
                "Video Editing",
                R.drawable.a,
                R.drawable.profilepic
        ));
    }

    private void showCard(int index) {
        if (index >= matchCards.size()) {
            // No more cards
            showNoMoreCards();
            return;
        }

        cardContainer.removeAllViews();
        
        MatchCard card = matchCards.get(index);
        View cardView = LayoutInflater.from(this).inflate(R.layout.item_match_card, cardContainer, false);
        
        // Populate card data
        TextView txtSkillCategory = cardView.findViewById(R.id.txt_skill_category);
        TextView txtDescription = cardView.findViewById(R.id.txt_description);
        TextView txtName = cardView.findViewById(R.id.txt_name);
        TextView txtStudio = cardView.findViewById(R.id.txt_studio);
        TextView txtRating = cardView.findViewById(R.id.txt_rating);
        TextView txtTeaches = cardView.findViewById(R.id.txt_teaches);
        TextView txtWantsLearn = cardView.findViewById(R.id.txt_wants_learn);
        ImageView imgPreview = cardView.findViewById(R.id.img_preview);
        CircleImageView imgProfile = cardView.findViewById(R.id.img_profile);

        txtSkillCategory.setText(card.skillCategory);
        txtDescription.setText(card.description);
        txtName.setText(card.name);
        txtStudio.setText(card.studio);
        txtRating.setText("★ " + card.rating);
        txtTeaches.setText("TEACHES: " + card.teaches);
        txtWantsLearn.setText("WANTS TO LEARN: " + card.wantsToLearn);
        imgPreview.setImageResource(card.previewImage);
        imgProfile.setImageResource(card.profileImage);

        // Add touch listener for swipe
        cardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return handleCardTouch(v, event);
            }
        });

        cardContainer.addView(cardView);
        currentCardView = cardView;
    }

    private boolean handleCardTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = event.getRawX();
                initialY = event.getRawY();
                return true;

            case MotionEvent.ACTION_MOVE:
                float deltaX = event.getRawX() - initialX;
                float deltaY = event.getRawY() - initialY;
                view.setTranslationX(deltaX);
                view.setTranslationY(deltaY);
                view.setRotation(deltaX / 20);
                return true;

            case MotionEvent.ACTION_UP:
                float finalDeltaX = event.getRawX() - initialX;
                
                if (Math.abs(finalDeltaX) > SWIPE_THRESHOLD) {
                    if (finalDeltaX > 0) {
                        // Swiped right - Accept
                        animateCardOut(view, true);
                    } else {
                        // Swiped left - Reject
                        animateCardOut(view, false);
                    }
                } else {
                    // Return to center
                    view.animate()
                            .translationX(0)
                            .translationY(0)
                            .rotation(0)
                            .setDuration(200)
                            .start();
                }
                return true;
        }
        return false;
    }

    private void animateCardOut(View view, boolean accepted) {
        float endX = accepted ? 2000f : -2000f;
        
        view.animate()
                .translationX(endX)
                .rotation(accepted ? 45f : -45f)
                .alpha(0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (accepted) {
                            showMatchConfirmation();
                        } else {
                            currentCardIndex++;
                            showCard(currentCardIndex);
                        }
                    }
                })
                .start();
    }

    private void acceptCard() {
        if (currentCardView != null) {
            animateCardOut(currentCardView, true);
        }
    }

    private void rejectCard() {
        if (currentCardView != null) {
            animateCardOut(currentCardView, false);
        }
    }

    private void showMatchConfirmation() {
        if (currentCardIndex < matchCards.size()) {
            MatchCard card = matchCards.get(currentCardIndex);
            Intent intent = new Intent(MatchActivity.this, MatchConfirmationActivity.class);
            intent.putExtra("match_name", card.name);
            intent.putExtra("match_skill", card.teaches);
            intent.putExtra("user_skill", card.wantsToLearn);
            intent.putExtra("match_score", (int)(card.rating * 20));
            startActivity(intent);
            
            currentCardIndex++;
            showCard(currentCardIndex);
        }
    }

    private void showNoMoreCards() {
        cardContainer.removeAllViews();
        TextView noCardsText = new TextView(this);
        noCardsText.setText("No more matches!\nCheck back later.");
        noCardsText.setTextColor(0xFFAAAAAA);
        noCardsText.setTextSize(18);
        noCardsText.setGravity(android.view.Gravity.CENTER);
        cardContainer.addView(noCardsText);
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_match);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            startActivity(new Intent(MatchActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(MatchActivity.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_work) {
            startActivity(new Intent(MatchActivity.this, WorkActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_match) {
            return true; // Already on match page
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(MatchActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        }

        return false;
    }

    // Match Card Data Model
    private static class MatchCard {
        String skillCategory;
        String description;
        String name;
        String studio;
        float rating;
        String teaches;
        String wantsToLearn;
        int previewImage;
        int profileImage;

        MatchCard(String skillCategory, String description, String name, String studio,
                 float rating, String teaches, String wantsToLearn, int previewImage, int profileImage) {
            this.skillCategory = skillCategory;
            this.description = description;
            this.name = name;
            this.studio = studio;
            this.rating = rating;
            this.teaches = teaches;
            this.wantsToLearn = wantsToLearn;
            this.previewImage = previewImage;
            this.profileImage = profileImage;
        }
    }
}
