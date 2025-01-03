package com.example.madfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
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

public class ViewBranchActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private LinearLayout branchContainer, branchCard ;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_branch_test); // Make sure this layout exists

        // Initialize Firestore instance
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        branchContainer = findViewById(R.id.branchContainer);
        scrollView = findViewById(R.id.scrollView);
        branchCard = findViewById(R.id.branchCard);

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
            Intent intent = new Intent(ViewBranchActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewBranchActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        // Load branch data from Firestore
        loadBranchData();
    }

    private void loadBranchData() {
        firestore.collection("Branch") // Adjusted collection name
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String branchName = documentSnapshot.getString("branch_name");
                            String branchEmail = documentSnapshot.getString("email_address");
                            String branchAddress = documentSnapshot.getString("branch_address");

                            // Handle branch_contact_number, check if it's a string or a number
                            Object contactNumberObj = documentSnapshot.get("branch_contact_number");
                            Object postalCodeObj = documentSnapshot.get("postal_code"); // Get the postal_code field as Object
                            Object mapViewObj = documentSnapshot.get("map_view");

                            String branchContactNumber = "";
                            String postalCode = "";
                            String mapView = "";

                            // Check and convert the contact number to string if it's not a string
                            if (contactNumberObj != null) {
                                if (contactNumberObj instanceof String) {
                                    branchContactNumber = (String) contactNumberObj;
                                } else if (contactNumberObj instanceof Number) {
                                    branchContactNumber = String.valueOf(contactNumberObj);
                                }
                            }

                            // Check and convert postal code to string if it's not a string
                            if (postalCodeObj != null) {
                                if (postalCodeObj instanceof String) {
                                    postalCode = (String) postalCodeObj;
                                } else if (postalCodeObj instanceof Number) {
                                    postalCode = String.valueOf(postalCodeObj);
                                }
                            }

                            // Check and convert map view to string if it's not a string
                            if (mapViewObj != null) {
                                if (mapViewObj instanceof String) {
                                    mapView = (String) mapViewObj;
                                } else if (mapViewObj instanceof Number) {
                                    mapView = String.valueOf(mapViewObj);
                                }
                            }

                            //First We need to delete branch cards existing
                            deleteBranchCard(branchCard);

                            // Create dynamic branch cards
                            createBranchCard(branchName, branchEmail, branchAddress, branchContactNumber, postalCode, mapView);
                        }
                    } else {
                        Toast.makeText(ViewBranchActivity.this, "No branches found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ViewBranchActivity.this, "Failed to load branch data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    private void createBranchCard(String branchName, String branchEmail, String branchAddress,
                                  String branchContactNumber, String postalCode, String mapView) {
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

        // Create and set Branch Address TextView
        TextView branchAddressTextView = new TextView(this);
        branchAddressTextView.setText(branchAddress);
        branchAddressTextView.setTextSize(16);
        branchAddressTextView.setTextColor(getResources().getColor(R.color.black));
        branchCard.addView(branchAddressTextView);

        // Create and set Branch Contact Number TextView
        TextView branchContactNumberTextView = new TextView(this);
        branchContactNumberTextView.setText(branchContactNumber);
        branchContactNumberTextView.setTextSize(16);
        branchContactNumberTextView.setTextColor(getResources().getColor(R.color.black));
        branchCard.addView(branchContactNumberTextView);

        // Create and set Postal Code TextView
        TextView postalCodeTextView = new TextView(this);
        postalCodeTextView.setText(postalCode);
        postalCodeTextView.setTextSize(16);
        postalCodeTextView.setTextColor(getResources().getColor(R.color.black));
        branchCard.addView(postalCodeTextView);

        // Create Location Button
        Button branchLocationBtn = new Button(this);
        branchLocationBtn.setText("View Map");
        branchLocationBtn.setTextSize(16);
        branchLocationBtn.setTextColor(getResources().getColor(R.color.white));
        branchLocationBtn.setBackgroundResource(R.drawable.button_shape);
        branchLocationBtn.setBackgroundTintList(getResources().getColorStateList(R.color.SecondaryColor));
        branchLocationBtn.setOnClickListener(v -> {
            // Handle location button click (example: show map or navigate)
            Toast.makeText(ViewBranchActivity.this, "Navigating to branch map...", Toast.LENGTH_SHORT).show();
        });
        branchCard.addView(branchLocationBtn);

        // Add branch card to the container
        branchContainer.addView(branchCard);
    }

    private void deleteBranchCard(LinearLayout branchCard) {
        // Remove the branch card from the UI
        branchContainer.removeView(branchCard);
    }

}