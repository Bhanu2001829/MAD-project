package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FogetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foget_password);  // Make sure this matches your layout file name



        Button backButton = findViewById(R.id.L_p_btn_4);
        Button resetButton = findViewById(R.id.L_p_btn_3);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to Login page
                Intent intent = new Intent(FogetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Optional: Finish current activity to remove it from the back stack
            }
        });
        Button resetPasswordButton = findViewById(R.id.L_p_btn_3);
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailField = findViewById(R.id.emailEditText);  // Make sure you have the correct ID for your username field
                String email = emailField.getText().toString();

                if (!email.isEmpty()) {
                    // Trigger password reset (This could be API logic or local logic)
                    // You can add API logic here to send a reset link via email or other methods
                    Toast.makeText(FogetPasswordActivity.this, "Password reset link sent to your email!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FogetPasswordActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Sign-in Activity
                Intent intent = new Intent(FogetPasswordActivity.this, EnterNewPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}