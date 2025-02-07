package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegistrationSuccessfulyActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_successfuly);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);


        // Call the method to handle the delayed transition
        switchToMainFormActivityAfterDelay();
    }

    /**
     * Adds a delay before transitioning to the next activity (activity_main).
     */
    private void switchToMainFormActivityAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(RegistrationSuccessfulyActivity.this, GuestHomeActivity.class);
            startActivity(intent);
            finish(); // Close activity_main activity
        }, DELAY_TIME);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(RegistrationSuccessfulyActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(RegistrationSuccessfulyActivity.this, GuestHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(RegistrationSuccessfulyActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}