package com.example.maddashboard;  // Replace with your package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FogetPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);  // Make sure this matches your layout file name

        Button backButton = findViewById(R.id.L_p_btn_4);
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
                EditText usernameField = findViewById(R.id.edt_username);  // Make sure you have the correct ID for your username field
                String username = usernameField.getText().toString();

                if (!username.isEmpty()) {
                    // Trigger password reset (This could be API logic or local logic)
                    // You can add API logic here to send a reset link via email or other methods
                    Toast.makeText(FogetPasswordActivity.this, "Password reset link sent to your email!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FogetPasswordActivity.this, "Please enter your username.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
