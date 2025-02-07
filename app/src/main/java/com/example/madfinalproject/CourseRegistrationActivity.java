package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CourseRegistrationActivity extends AppCompatActivity {

    // UI Elements for adding a user
    private EditText firstNameEditText, lastNameEditText, nicEditText, emailEditText, birthdayEditText, addressEditText, contactNumberEditText;
    private RadioButton maleRadioButton, femaleRadioButton;
    private Button nextButton;

    // Firebase Firestore instance
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_registration);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        // Set up bottom navigation to switch Activities
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);


        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI Elements
        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        nicEditText = findViewById(R.id.nic);
        emailEditText = findViewById(R.id.emailAddress);
        birthdayEditText = findViewById(R.id.birthday);
        addressEditText = findViewById(R.id.address);
        contactNumberEditText = findViewById(R.id.contactNumber);
        maleRadioButton = findViewById(R.id.male);
        femaleRadioButton = findViewById(R.id.female);
        nextButton = findViewById(R.id.userbtn);

        // Initialize UI Elements for navigation
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        // Set listener for Next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddUser();
            }
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(CourseRegistrationActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(CourseRegistrationActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(CourseRegistrationActivity.this, StudentViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(CourseRegistrationActivity.this, GuestHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(CourseRegistrationActivity.this, StudentViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void handleAddUser() {
        // Retrieve user input
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String nic = nicEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String birthday = birthdayEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();
        String gender = maleRadioButton.isChecked() ? "Male" : (femaleRadioButton.isChecked() ? "Female" : "");

        // Validate input
        if (firstName.isEmpty() || lastName.isEmpty() || nic.isEmpty() || email.isEmpty() || birthday.isEmpty() || address.isEmpty() || contactNumber.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map for user data
        Map<String, String> studentData = new HashMap<>();
        studentData.put("firstName", firstName);
        studentData.put("lastName", lastName);
        studentData.put("nic", nic);
        studentData.put("email", email);
        studentData.put("birthday", birthday);
        studentData.put("address", address);
        studentData.put("contactNumber", contactNumber);
        studentData.put("gender", gender);

        // Add student data to Firestore
        firestore.collection("Student") // "Student" is the Firestore collection name
                .add(studentData)
                .addOnSuccessListener(new OnSuccessListener<com.google.firebase.firestore.DocumentReference>() {
                    @Override
                    public void onSuccess(com.google.firebase.firestore.DocumentReference documentReference) {
                        Toast.makeText(CourseRegistrationActivity.this, "User added successfully!", Toast.LENGTH_SHORT).show();
                        resetFields();
                        Intent uploadImage = new Intent(CourseRegistrationActivity.this, UploadImageActivity.class);
                        uploadImage.putExtra("email",email);
                        startActivity(uploadImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CourseRegistrationActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void resetFields() {
        // Clear all input fields after successful submission
        firstNameEditText.setText("");
        lastNameEditText.setText("");
        nicEditText.setText("");
        emailEditText.setText("");
        birthdayEditText.setText("");
        addressEditText.setText("");
        contactNumberEditText.setText("");
        maleRadioButton.setChecked(false);
        femaleRadioButton.setChecked(false);
    }
}
