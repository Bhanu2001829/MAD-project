package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DdsCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.dds_course);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        // Set up bottom navigation to switch Activities
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycleView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(DdsCourseActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(DdsCourseActivity.this, GuestHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(DdsCourseActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}