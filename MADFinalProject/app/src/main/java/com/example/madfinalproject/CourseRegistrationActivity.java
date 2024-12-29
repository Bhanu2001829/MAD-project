package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
=======
>>>>>>> Stashed changes

public class CourseRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.course_registration);

<<<<<<< Updated upstream
        Button applybtn = findViewById(R.id.applybtn);
=======
        Button applybtn = findViewById(R.id.userbtn);
>>>>>>> Stashed changes
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the forget password screen
                Intent intent = new Intent(CourseRegistrationActivity.this, UploadImageActivity.class);
                startActivity(intent);
            }
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(CourseRegistrationActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(CourseRegistrationActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

    }
}