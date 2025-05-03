package com.example.skillshub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CourseLearn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_learn);

        // Get the skill name passed from LearnActivity
        String skillName = getIntent().getStringExtra("skillName");

        // Reference to the Course Title TextView
        TextView courseTitle = findViewById(R.id.txt_course_title);
        if (skillName != null && courseTitle != null) {
            courseTitle.setText(skillName);
        } else {
            Toast.makeText(this, "Skill name not found!", Toast.LENGTH_SHORT).show();
        }

        // Add Paid Courses
        LinearLayout paidCoursesLayout = findViewById(R.id.paidCoursesLayout);
        if (paidCoursesLayout != null) {
            addCourseLink(paidCoursesLayout, "Udemy - Adobe Photoshop Masterclass", "https://www.udemy.com/course/adobe-photoshop-masterclass/");
            addCourseLink(paidCoursesLayout, "Coursera - Graphic Design Specialization", "https://www.coursera.org/specializations/graphic-design");
        }

        // Add Free Courses
        LinearLayout freeCoursesLayout = findViewById(R.id.layout_free_courses);
        if (freeCoursesLayout != null) {
            addCourseLink(freeCoursesLayout, "Canva Design School", "https://www.canva.com/learn/design-school/");
            addCourseLink(freeCoursesLayout, "Envato Tuts+ Free Graphic Design Courses", "https://tutsplus.com/courses/free");
        }

        // Add Roadmap Links
        LinearLayout roadmapLayout = findViewById(R.id.layout_roadmap);

        if (roadmapLayout != null) {
            addCourseLink(roadmapLayout, "Complete Roadmap for Graphic Design", "https://www.careerfoundry.com/en/blog/ui-design/graphic-designer-career-path/");
        }
    }

    private void addCourseLink(LinearLayout parentLayout, String title, String url) {
        TextView linkView = (TextView) LayoutInflater.from(this)
                .inflate(R.layout.item_course_link, parentLayout, false);

        linkView.setText(title);
        linkView.setOnClickListener(v -> openWebLink(url));
        parentLayout.addView(linkView);
    }

    private void openWebLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
