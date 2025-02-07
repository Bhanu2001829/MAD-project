package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ViewCourseActivity extends AppCompatActivity {

    private FirebaseDatabaseHelper dbHelper;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.view_course);

        ImageButton profileButton = findViewById(R.id.profilebtn);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        BottomNavigationView bottomNavigationView;
        // Set up bottom navigation to switch Activities
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        profileButton.setOnClickListener(v -> {
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

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(ViewCourseActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(ViewCourseActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(ViewCourseActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }


}