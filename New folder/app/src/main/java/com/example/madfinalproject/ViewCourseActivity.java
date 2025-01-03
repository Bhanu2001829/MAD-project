package com.example.madfinalproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ViewCourseActivity extends AppCompatActivity {

    private FirebaseDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_course);

        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(ViewCourseActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(ViewCourseActivity.this, NotificationActivity.class);
            startActivity(intent);
        });


        // Initialize Firebase Database Helper
        dbHelper = new FirebaseDatabaseHelper();

        // TextViews for course details
        TextView courseNameText = findViewById(R.id.lcourse_name);
        TextView categoryText = findViewById(R.id.Categoty);
        TextView durationText = findViewById(R.id.Duration);
        TextView startDateText = findViewById(R.id.lstarting_date);
        TextView endDateText = findViewById(R.id.lclosing_date);
        TextView feeText = findViewById(R.id.courseFee);

        // Sample course data (in a real app, you would fetch this from Firestore or pass it via Intent)
        String courseId = "12345"; // Example course ID
        String courseName = "Introduction to Android";
        String category = "Programming";
        String duration = "6 weeks";
        String startDate = "2024-01-15";
        String endDate = "2024-02-28";
        String courseFee = "100 USD";

        // Display course information
        courseNameText.setText(courseName);
        categoryText.setText(category);
        durationText.setText(duration);
        startDateText.setText(startDate);
        endDateText.setText(endDate);
        feeText.setText(courseFee);


    }
}