package com.example.madfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

<<<<<<< Updated upstream
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
>>>>>>> Stashed changes

public class AddCourseActivity extends AppCompatActivity {

    private EditText courseNameEditText, courseFeeEditText, durationCountEditText, startDateEditText, endDateEditText;
    private Spinner categorySpinner, branchSpinner, badgeSpinner, durationSpinner;
    private Button createButton;
<<<<<<< Updated upstream
=======
    private FirebaseFirestore firestore; // Firebase Firestore instance
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course); // Replace with your actual layout file name

<<<<<<< Updated upstream
=======
        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

>>>>>>> Stashed changes
        // Initialize views
        courseNameEditText = findViewById(R.id.cource_name);
        courseFeeEditText = findViewById(R.id.course_fee);
        durationCountEditText = findViewById(R.id.count_number);
<<<<<<< Updated upstream
        startDateEditText = findViewById(R.id.starting_date);
        endDateEditText = findViewById(R.id.lend_date);
        categorySpinner = findViewById(R.id.spinner_category);
        branchSpinner = findViewById(R.id.spinner_branch);
        badgeSpinner = findViewById(R.id.spinner_badge);
=======
        startDateEditText = findViewById(R.id.course_starting_date);
        endDateEditText = findViewById(R.id.course_end_date);
        categorySpinner = findViewById(R.id.course_category);
        branchSpinner = findViewById(R.id.course_branch);
        badgeSpinner = findViewById(R.id.course_badge);
>>>>>>> Stashed changes
        durationSpinner = findViewById(R.id.year_month);
        createButton = findViewById(R.id.createBtn);

        // Populate spinners
        populateCategorySpinner();
        populateBranchSpinner();
        populateBadgeSpinner();
        populateDurationSpinner();

        // Set listener for Create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreateCourse();
            }
        });
    }

    private void populateCategorySpinner() {
<<<<<<< Updated upstream
        // Example categories
=======
>>>>>>> Stashed changes
        ArrayList<String> categories = new ArrayList<>(Arrays.asList("Science", "Technology", "Mathematics", "Engineering"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void populateBranchSpinner() {
<<<<<<< Updated upstream
        // Example branches
=======
>>>>>>> Stashed changes
        ArrayList<String> branches = new ArrayList<>(Arrays.asList("Colombo", "Kandy", "Galle", "Jaffna"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branches);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(adapter);
    }

    private void populateBadgeSpinner() {
<<<<<<< Updated upstream
        // Example badges
=======
>>>>>>> Stashed changes
        ArrayList<String> badges = new ArrayList<>(Arrays.asList("Beginner", "Intermediate", "Advanced", "Professional"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, badges);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        badgeSpinner.setAdapter(adapter);
    }

    private void populateDurationSpinner() {
<<<<<<< Updated upstream
        // Example durations
=======
>>>>>>> Stashed changes
        ArrayList<String> durations = new ArrayList<>(Arrays.asList("Days", "Months", "Years"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, durations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);
    }

    private void handleCreateCourse() {
        String courseName = courseNameEditText.getText().toString().trim();
        String courseFee = courseFeeEditText.getText().toString().trim();
        String durationCount = durationCountEditText.getText().toString().trim();
        String startDate = startDateEditText.getText().toString().trim();
        String endDate = endDateEditText.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();
        String branch = branchSpinner.getSelectedItem().toString();
        String badge = badgeSpinner.getSelectedItem().toString();
        String durationType = durationSpinner.getSelectedItem().toString();

<<<<<<< Updated upstream
        // Validate input fields
=======
>>>>>>> Stashed changes
        if (courseName.isEmpty() || courseFee.isEmpty() || durationCount.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
<<<<<<< Updated upstream
            double fee = Double.parseDouble(courseFee); // Parse fee to ensure it's numeric
            int duration = Integer.parseInt(durationCount); // Parse duration count to ensure it's numeric

            // Example: Save course details (you can save this to a database or pass to a server)
            String courseDetails = "Course Name: " + courseName +
                    "\nCategory: " + category +
                    "\nBranch: " + branch +
                    "\nBadge: " + badge +
                    "\nCourse Fee: " + fee +
                    "\nDuration: " + duration + " " + durationType +
                    "\nStart Date: " + startDate +
                    "\nEnd Date: " + endDate;

            Toast.makeText(this, "Course Created:\n" + courseDetails, Toast.LENGTH_LONG).show();

            // Reset fields after creating course
            resetFields();
=======
            double fee = Double.parseDouble(courseFee);
            int duration = Integer.parseInt(durationCount);

            // Create a map for course data
            Map<String, Object> courseData = new HashMap<>();
            courseData.put("courseName", courseName);
            courseData.put("courseFee", fee);
            courseData.put("durationCount", duration);
            courseData.put("startDate", startDate);
            courseData.put("endDate", endDate);
            courseData.put("category", category);
            courseData.put("branch", branch);
            courseData.put("badge", badge);
            courseData.put("durationType", durationType);

            // Add course data to Firestore
            firestore.collection("courses")
                    .add(courseData)
                    .addOnSuccessListener(new OnSuccessListener<com.google.firebase.firestore.DocumentReference>() {
                        @Override
                        public void onSuccess(com.google.firebase.firestore.DocumentReference documentReference) {
                            Toast.makeText(AddCourseActivity.this, "Course added successfully!", Toast.LENGTH_SHORT).show();
                            resetFields();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddCourseActivity.this, "Failed to add course: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
>>>>>>> Stashed changes

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values for Course Fee and Duration", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        courseNameEditText.setText("");
        courseFeeEditText.setText("");
        durationCountEditText.setText("");
        startDateEditText.setText("");
        endDateEditText.setText("");
        categorySpinner.setSelection(0);
        branchSpinner.setSelection(0);
        badgeSpinner.setSelection(0);
        durationSpinner.setSelection(0);
    }
}
