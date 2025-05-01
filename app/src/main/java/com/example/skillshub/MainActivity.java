package com.example.skillshub;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home); // Ensure home is selected

        // Setup first carousel
        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.appdev, "App Dev"));
        list.add(new CarouselItem(R.drawable.webdev, "WebDev"));
        list.add(new CarouselItem(R.drawable.graphdesign, "GraphDesign"));
        list.add(new CarouselItem(R.drawable.videoedit, "Video-Edit"));
        list.add(new CarouselItem(R.drawable.logodesign, "Logo Design"));

        CarouselAdapter adapter = new CarouselAdapter(this, list);
        recyclerView.setAdapter(adapter);

        // Setup expert carousel
        RecyclerView expertRecyclerView = findViewById(R.id.expertCarouselRecyclerView);
        expertRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        List<CardItem> expertList = new ArrayList<>();
        expertList.add(new CardItem("John Doe", "App Developer", "Experienced in building scalable Android applications.",
                "₹1,200/hr", 4.8f, R.drawable.profileicon,
                Arrays.asList("Java", "Kotlin", "Firebase")));
        expertList.add(new CardItem("Jane Smith", "UI/UX Designer", "Designs intuitive and visually appealing interfaces.",
                "₹1,000/hr", 4.9f, R.drawable.profileicon,
                Arrays.asList("Figma", "Adobe XD", "Sketch")));
        expertList.add(new CardItem("Mike Johnson", "Web Developer", "Full-stack developer specializing in MERN stack.",
                "₹1,500/hr", 4.7f, R.drawable.profileicon,
                Arrays.asList("React", "Node.js", "MongoDB")));
        expertList.add(new CardItem("Sarah Williams", "Graphic Designer", "Creates brand-focused digital graphics.",
                "₹900/hr", 4.6f, R.drawable.profileicon,
                Arrays.asList("Photoshop", "Illustrator", "Canva")));

        CardAdapter expertAdapter = new CardAdapter(this, expertList);
        expertRecyclerView.setAdapter(expertAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_home) {
            return true;
        } else if (itemId == R.id.nav_ideas) {
            startActivity(new Intent(MainActivity.this, LearnActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_work) {
            startActivity(new Intent(MainActivity.this, WorkActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_notifications) {
            startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
            overridePendingTransition(0, 0);
            return true;
        } else if (itemId == R.id.nav_profile) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            return true;
        }

        return false;
    }
}
