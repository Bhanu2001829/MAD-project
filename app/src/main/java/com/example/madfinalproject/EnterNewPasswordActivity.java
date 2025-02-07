package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EnterNewPasswordActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_new_password); // Ensure XML layout file name matches



        // Initialize the button
        Button saveBtn = findViewById(R.id.save_btn);

        // Set click listener on the Save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to savepasswordsuccessfuly Activity
                Intent intent = new Intent(EnterNewPasswordActivity.this, ChangePasswordSuccessfullyActivity.class);
                startActivity(intent);
            }
        });
    }

}