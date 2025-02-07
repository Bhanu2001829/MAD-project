package com.example.madfinalproject;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddUserActivity extends AppCompatActivity {

    // Declare UI elements
    private EditText firstNameEditText, lastNameEditText, userNameEditText, nicEditText, emailEditText, birthdayEditText, addressEditText;
    private Button createAdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        // Set up bottom navigation to switch Activities
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Initialize the UI elements
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        userNameEditText = findViewById(R.id.nic);
        nicEditText = findViewById(R.id.nic);
        emailEditText = findViewById(R.id.emailAddress);
        birthdayEditText = findViewById(R.id.birthday);
        addressEditText = findViewById(R.id.address);
        createAdminButton = findViewById(R.id.addUser);

        // Set the OnClickListener for the "Create User" button
        createAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAdminUser();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(AddUserActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(AddUserActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(AddUserActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    // Method to handle "Create Admin User" button click
    private void createAdminUser() {
        // Get the text from each EditText field
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String userName = userNameEditText.getText().toString().trim();
        String nic = nicEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String birthday = birthdayEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate that the fields are not empty
        if (firstName.isEmpty() || lastName.isEmpty() || userName.isEmpty() || nic.isEmpty() || email.isEmpty() || birthday.isEmpty() || address.isEmpty()) {
            Toast.makeText(AddUserActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(AddUserActivity.this, "Admin Created: " + firstName + " " + lastName, Toast.LENGTH_SHORT).show();

            // Optionally, you can clear the fields after the data has been saved
            clearFields();
        }
    }

    // Method to clear the input fields after successful creation
    private void clearFields() {
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        userNameEditText.setText("");
        nicEditText.setText("");
        emailEditText.setText("");
        birthdayEditText.setText("");
        addressEditText.setText("");
    }
}
