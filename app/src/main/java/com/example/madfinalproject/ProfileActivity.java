package com.example.madfinalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText firstname, lastname, nic, address, contactNumber, emailAddress;
    private ImageView profileImage;
    private Button updatebtn, logoutbtn;
    private FirebaseFirestore firestore;
    private String userEmail; // Store user email

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        checkAndRequestPermissions(); // Request permissions

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Get UI elements
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        nic = findViewById(R.id.nic);
        address = findViewById(R.id.address);
        contactNumber = findViewById(R.id.contactNumber);
        emailAddress = findViewById(R.id.emailAddress);
        profileImage = findViewById(R.id.imageButton);
        updatebtn = findViewById(R.id.userbtn);
        logoutbtn = findViewById(R.id.log_out_btn);

        // Get email from Intent
        userEmail = getIntent().getStringExtra("email");

        if (userEmail != null) {
            emailAddress.setText(userEmail);
            fetchUserData(userEmail); // Fetch user details
            fetchUserPhoto(userEmail); // Fetch user photo
        } else {
            Toast.makeText(this, "User email not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update button listener
        updatebtn.setOnClickListener(v -> updateUserData());

        // Logout button listener
        logoutbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Function to request runtime permissions
     */
    private void checkAndRequestPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) { // For Android 9 and below
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    /**
     * Handle runtime permission results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied! Cannot access media.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Fetch user photo from Firestore and load it into ImageButton
     */
    private void fetchUserPhoto(String userEmail) {
        firestore.collection("images")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            String imageUri = document.getString("imageUri");

                            if (imageUri != null && !imageUri.isEmpty()) {
                                Uri imageUriParsed = Uri.parse(imageUri); // Parse content URI

                                Log.d("Firestore Image URI", imageUri); // Debugging

                                // Set image directly from content URI
                                profileImage.setImageURI(imageUriParsed);

                                Toast.makeText(ProfileActivity.this, "Image Loaded!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(ProfileActivity.this, "User image not found in Firestore!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(ProfileActivity.this, "Error fetching image: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }


    /**
     * Fetch user details from Firestore
     */
    private void fetchUserData(String userEmail) {
        firestore.collection("Student")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            firstname.setText(document.getString("firstName"));
                            lastname.setText(document.getString("lastName"));
                            nic.setText(document.getString("nic"));
                            address.setText(document.getString("address"));
                            contactNumber.setText(document.getString("contactNumber"));
                            Toast.makeText(this, "Details Found: " + userEmail, Toast.LENGTH_SHORT).show();
                            break;
                        }
                    } else {
                        Toast.makeText(this, "User not found in Student collection!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error fetching details: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    /**
     * Updates user data in Firestore
     */
    private void updateUserData() {
        if (userEmail == null) {
            Toast.makeText(this, "User email is missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert email to Firestore-safe document ID
        String safeEmail = userEmail.replace(".", "_").replace("@", "_");

        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", firstname.getText().toString().trim());
        userData.put("lastName", lastname.getText().toString().trim());
        userData.put("nic", nic.getText().toString().trim());
        userData.put("address", address.getText().toString().trim());
        userData.put("contactNumber", contactNumber.getText().toString().trim());

        firestore.collection("Student").document(safeEmail)
                .set(userData)
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Profile update failed!", Toast.LENGTH_SHORT).show()
                );
    }
}
