package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentViewCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_view_course);

        Button applybtn = findViewById(R.id.applyCourseBtn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login Activity
                Intent intent = new Intent(StudentViewCourseActivity.this, CourseRegistrationActivity.class);
                startActivity(intent);
            }
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(StudentViewCourseActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(StudentViewCourseActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

    }
}