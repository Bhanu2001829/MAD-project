package com.example.madfinalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class StudentNotificationActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private LinearLayout branchContainer, branchCard ;
    private ScrollView scrollView;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_notification); // Make sure this layout exists

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        branchContainer = findViewById(R.id.notificationContainer);
        scrollView = findViewById(R.id.scrollView4);
        branchCard = findViewById(R.id.notifiction_details);

        if (branchContainer == null || scrollView == null) {
            // If the views are null, log the error
            Toast.makeText(this, "Error: UI elements not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize header buttons
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

        if (menuimgbtn == null || notificationimgbtn == null) {
            // If the buttons are null, log the error
            Toast.makeText(this, "Error: Button views not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Set click listeners for menu and notification buttons
        menuimgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentNotificationActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(StudentNotificationActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        // Load branch data from Firestore
        loadBranchData();
    }

    private void loadBranchData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        firestore.collection("Notification") // Adjusted collection name
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String branchName = documentSnapshot.getString("notification_topic");
                            String branchEmail = documentSnapshot.getString("notification_details");


                            // Create dynamic branch cards after loading data
                            new android.os.Handler().postDelayed(() -> {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                // Remove existing branch cards before adding new ones
                                deleteBranchCard(branchCard);
                            }, 2000); // 2000 milliseconds = 2 seconds

                            // Create and display the branch card
                            createBranchCard(branchName, branchEmail);
                        }
                    } else {
                        Toast.makeText(StudentNotificationActivity.this, "No Notifications found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(StudentNotificationActivity.this, "Failed to load notification data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void createBranchCard(String branchName, String branchEmail) {
        if (branchName == null || branchEmail == null) {
            // Skip creating card if any essential field is null
            return;
        }

        // Create a new LinearLayout for the branch card
        LinearLayout branchCard = new LinearLayout(this);
        branchCard.setOrientation(LinearLayout.VERTICAL);
        branchCard.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        branchCard.setPadding(16, 16, 16, 16);
        branchCard.setBackgroundResource(R.drawable.button_shape); // Apply background shape for card

        // Create and set Branch Name TextView
        TextView branchNameTextView = new TextView(this);
        branchNameTextView.setText(branchName);
        branchNameTextView.setTextSize(20);
        branchNameTextView.setGravity(Gravity.CENTER);
        branchNameTextView.setTextColor(getResources().getColor(R.color.thirdColor));
        branchCard.addView(branchNameTextView);

        // Create and set Branch Email TextView
        TextView branchEmailTextView = new TextView(this);
        branchEmailTextView.setText(branchEmail);
        branchEmailTextView.setTextSize(16);
        branchEmailTextView.setTextColor(getResources().getColor(R.color.black));
        branchCard.addView(branchEmailTextView);

        // Add branch card to the container
        branchContainer.addView(branchCard);
    }

    private void deleteBranchCard(LinearLayout branchCard) {
        // Remove the branch card from the UI
        branchContainer.removeView(branchCard);
    }

}
