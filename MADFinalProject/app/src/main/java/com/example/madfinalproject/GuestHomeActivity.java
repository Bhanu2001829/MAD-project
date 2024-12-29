package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GuestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.guest_home);

        ImageButton coursebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.branch_btn);
        ImageButton registerbtn = findViewById(R.id.register_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);




        coursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login Activity
                Intent intent = new Intent(GuestHomeActivity.this, StudentViewCourseActivity.class);
                startActivity(intent);
            }
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(GuestHomeActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        branchbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, StudentViewBranchActivity.class);
            startActivity(intent);
        });
        branchbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, StudentViewBranchActivity.class);
            startActivity(intent);
        });


    }
}