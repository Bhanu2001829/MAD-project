package com.example.madfinalproject;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Call the method to handle the delayed transition
        switchToMainFormActivityAfterDelay();
    }

    /**
     * Adds a delay before transitioning to the next activity (activity_main).
     */
    private void switchToMainFormActivityAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
            startActivity(intent);
            finish(); // Close activity_main activity
        }, DELAY_TIME);
    }
}