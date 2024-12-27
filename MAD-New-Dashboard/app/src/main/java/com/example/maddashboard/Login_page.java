package com.example.maddashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login_page extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView registerTextView, forgetPasswordTextView;  // Add forgetPasswordTextView

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Set to the correct XML layout

        // Initialize views
        usernameEditText = findViewById(R.id.edt_username);
        passwordEditText = findViewById(R.id.edt_password);
        loginButton = findViewById(R.id.L_btn_3);
        registerTextView = findViewById(R.id.txt_regiter);
        forgetPasswordTextView = findViewById(R.id.txt_forget_pwd);  // Initialize Forget Password TextView

        // Set login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    // Show error if fields are empty
                    Toast.makeText(Login_page.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform login action (this can be replaced with actual authentication logic)
                    if (username.equals("admin") && password.equals("1234")) {
                        Toast.makeText(Login_page.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Navigate to the main activity or another page after successful login
                        Intent intent = new Intent(Login_page.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Login_page.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set register text click listener
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Sign-in activity
                Intent intent = new Intent(Login_page.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        // Set forget password text click listener
        forgetPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Forget Password activity
                Intent intent = new Intent(Login_page.this, FogetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
