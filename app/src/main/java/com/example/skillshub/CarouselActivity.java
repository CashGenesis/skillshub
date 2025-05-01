package com.example.skillshub;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

public class CarouselActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private LinearLayout indicatorsContainer;
    private List<CardItem> cardItems;
    private ImageView[] indicators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel);

        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        indicatorsContainer = findViewById(R.id.indicators_container);

        // Get sample data
        cardItems = CardItem.createSampleCards();

        // Setup ViewPager2
        CardAdapter adapter = new CardAdapter(this, cardItems);
        viewPager.setAdapter(adapter);

        // Add page transformation effect (optional)
        viewPager.setOffscreenPageLimit(1);
        float nextItemVisiblePx = getResources().getDimension(R.dimen.viewpager_next_item_visible);
        float currentItemHorizontalMarginPx = getResources().getDimension(R.dimen.viewpager_current_item_horizontal_margin);
        float pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx;

        ViewPager2.PageTransformer pageTransformer = new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setTranslationX(-pageTranslationX * position);

                // Scale the page down (between MIN_SCALE and 1)
                float MIN_SCALE = 0.85f;
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position * 0.15f));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            }
        };
        viewPager.setPageTransformer(pageTransformer);

        // Setup indicators
        setupIndicators();

        // Set page change listener
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateIndicators(position);
            }
        });
    }

    private void setupIndicators() {
        indicators = new ImageView[cardItems.size()];

        // Create indicator dots
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(this);
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(this,
                            i == 0 ? R.drawable.indicator_active : R.drawable.indicator_inactive)
            );

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            indicatorsContainer.addView(indicators[i], params);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(this,
                            i == position ? R.drawable.indicator_active : R.drawable.indicator_inactive)
            );
        }
    }
}
