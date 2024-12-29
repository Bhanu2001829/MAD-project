package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home); // Make sure the layout name is correct

        // Initialize ImageButtons
        ImageButton userbtn = findViewById(R.id.user_btn);
        ImageButton addcourcebtn = findViewById(R.id.add_cource_btn);
        ImageButton createbranchbtn = findViewById(R.id.create_branch_btn);
<<<<<<< Updated upstream
        ImageButton registerbtn = findViewById(R.id.register_btn);
=======
>>>>>>> Stashed changes
        ImageButton courcebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.branch_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        // Setting onClickListeners for ImageButtons
        userbtn.setOnClickListener(v -> {
            // Navigate to View Student Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewStudentActivity.class);
            startActivity(intent);
        });

        addcourcebtn.setOnClickListener(v -> {
            // Navigate to Add Course Activity
            Intent intent = new Intent(AdminHomeActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });

        createbranchbtn.setOnClickListener(v -> {
            // Navigate to Create Branch Activity
            Intent intent = new Intent(AdminHomeActivity.this, CreateBranchActivity.class);
            startActivity(intent);
        });

<<<<<<< Updated upstream
        registerbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(AdminHomeActivity.this, CourseRegistrationActivity.class);
            startActivity(intent);
        });
=======

>>>>>>> Stashed changes


        courcebtn.setOnClickListener(v -> {
            // Navigate to View Course Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewCourseActivity.class);
            startActivity(intent);
        });

        branchbtn.setOnClickListener(v -> {
            // Navigate to View Branch Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewBranchActivity.class);
            startActivity(intent);
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(AdminHomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(AdminHomeActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

           }
}
