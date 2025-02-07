package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView txtForgetPwd, txtRegister;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Ensure this matches your layout file name

        initializeViews();
        setupListeners();

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();
    }

    /**
     * Initializes all UI components.
     */
    private void initializeViews() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.L_btn_3);
        txtForgetPwd = findViewById(R.id.txt_forget_pwd);
        txtRegister = findViewById(R.id.txt_regiter);
    }

    /**
     * Sets up click listeners for buttons and text views.
     */
    private void setupListeners() {
        txtForgetPwd.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, FogetPasswordActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> handleLogin());

        txtRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Handles login functionality with Firestore authentication.
     */
    private void handleLogin() {
        String userName = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        // Validate input fields
        if (userName.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Handle hardcoded credentials first
        if (userName.equals("guest") && password.equals("123")) {
            Intent intent = new Intent(LoginActivity.this, GuestHomeActivity.class);
            startActivity(intent);
            return;
        } else if (userName.equals("admin") && password.equals("123")) {
            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            return;
        }

        // Authenticate with Firestore for other users
        firestore.collection("User")
                .whereEqualTo("userName", userName) // Query using userName field
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String storedPassword = document.getString("password");

                            if (storedPassword != null && storedPassword.equals(password)) {
                                // Navigate to GuestHomeActivity for all valid Firestore users
                                Intent intent = new Intent(LoginActivity.this, GuestHomeActivity.class);
                                intent.putExtra("userName", userName);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
