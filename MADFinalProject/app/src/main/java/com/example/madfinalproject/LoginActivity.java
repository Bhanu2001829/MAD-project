package com.example.madfinalproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);  // Make sure this matches your layout file name

        TextView forgetPwd = findViewById(R.id.txt_forget_pwd);
        Button login = findViewById(R.id.L_btn_3);
        TextView register = findViewById(R.id.txt_regiter);


        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the forget password screen
                Intent intent = new Intent(LoginActivity.this, FogetPasswordActivity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve username and password entered by the user
                EditText usernameEditText = findViewById(R.id.edt_username); // Replace with your actual EditText ID
                EditText passwordEditText = findViewById(R.id.edt_password); // Replace with your actual EditText ID

                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Check if username and password fields are not empty
                if (username.isEmpty()|| password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                                // Validate username and navigate accordingly
                if (username.equals("guest") && password.equals("123")) {
                    // Navigate to GuestHomeActivity
                    Intent intent = new Intent(LoginActivity.this, GuestHomeActivity.class);
                    startActivity(intent);
                } else if (username.equals("admin") && password.equals("123")) {
                    // Navigate to AdminHomeActivity
                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                    startActivity(intent);
                } else {
                    // Invalid username
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the forget password screen
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}