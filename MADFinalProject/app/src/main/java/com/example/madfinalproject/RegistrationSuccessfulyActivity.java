package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegistrationSuccessfulyActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registration_successfuly);

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
}