package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentViewBranchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_view_branch);

        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(StudentViewBranchActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(StudentViewBranchActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
    }
}