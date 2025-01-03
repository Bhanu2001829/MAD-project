package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        TextView loginText = findViewById(R.id.txt_sign_in);

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize Views
        usernameEditText = findViewById(R.id.edt_username);
        emailEditText = findViewById(R.id.edt_email);
        passwordEditText = findViewById(R.id.edt_password);
        confirmPasswordEditText = findViewById(R.id.edt_conform_password);
        signUpButton = findViewById(R.id.btn_3);

        // Sign-up button click listener
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateUser();
            }
        });

        // Login text click listener
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login Activity
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void handleCreateUser() {
        String userName = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate fields
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map for user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("userName", userName);
        userData.put("email", email);
        userData.put("password", password);

        // Add user data to Firestore
        firestore.collection("User")
                .add(userData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(SignInActivity.this, "User added successfully!", Toast.LENGTH_SHORT).show();
                        resetFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignInActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetFields() {
        usernameEditText.setText("");
        emailEditText.setText("");
        passwordEditText.setText("");
        confirmPasswordEditText.setText("");
    }
}
