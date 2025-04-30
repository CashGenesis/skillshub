package com.example.skillshub;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    }
}