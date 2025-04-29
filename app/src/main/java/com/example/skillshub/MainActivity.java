package com.example.skillshub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // List of popular skills
        String[] popularSkills = {"Video Editing", "Graphic Designing", "Web Development", "Logo Designing"};

        // Set up the ListView with the popular skills
        ListView skillsListView = findViewById(R.id.popular_skills_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, popularSkills);
        skillsListView.setAdapter(adapter);
    }
}

