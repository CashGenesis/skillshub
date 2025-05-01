package com.example.skillshub;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Ensure this matches your XML layout file

        // Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create list of items
        List<CarouselItem> list = new ArrayList<>();
        list.add(new CarouselItem(R.drawable.appdev, "App Dev"));
        list.add(new CarouselItem(R.drawable.webdev, "WebDev"));
        list.add(new CarouselItem(R.drawable.graphdesign, "GraphDesign"));
        list.add(new CarouselItem(R.drawable.videoedit, "Video-Edit"));
        list.add(new CarouselItem(R.drawable.logodesign, "Logo Design"));

        // Set Adapter
        CarouselAdapter adapter = new CarouselAdapter(this, list);
        recyclerView.setAdapter(adapter);


        RecyclerView expertRecyclerView = findViewById(R.id.expertCarouselRecyclerView);
        expertRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Create list of expert items for second carousel
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

        // Set Adapter for second carousel
        CardAdapter expertAdapter = new CardAdapter(this, expertList);
        expertRecyclerView.setAdapter(expertAdapter);
    }
}