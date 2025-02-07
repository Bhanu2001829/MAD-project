package com.example.madfinalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class GuestHomeActivity extends AppCompatActivity {

    private boolean isLocationPermissionGranted;
    private String userName;
    private String userEmail;  // Store the email after fetching

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_home);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Get username from Intent
        userName = getIntent().getStringExtra("userName");

        TextView nameTextView = findViewById(R.id.Nametxt);
        nameTextView.setText(userName != null ? userName : "Guest");

        // Fetch email for the given username
        if (userName != null) {
            fetchUserEmail(userName);
        }

        // Initialize Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        checkPermission();
        checkGooglePlayServices();

        ImageButton coursebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.Course1btn);
        ImageButton registerbtn = findViewById(R.id.register_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);
        ImageButton view = findViewById(R.id.weblinkbtn);
        ImageButton googleClassRoom = findViewById(R.id.gClassRoom);
        ImageButton reviewBtn = findViewById(R.id.reviewBtn);
        Button Course1btn = findViewById(R.id.C1courceId);
        Button Course2btn = findViewById(R.id.C2courceId);


        view.setOnClickListener(v -> openWebsite("https://www.nibm.lk"));
        googleClassRoom.setOnClickListener(v -> openWebsite("https://classroom.google.com/u/0/"));
        reviewBtn.setOnClickListener(v -> startActivity(new Intent(this, AddReviewActivity.class)));
        coursebtn.setOnClickListener(v -> startActivity(new Intent(this, StudentViewCourseActivity.class)));
        branchbtn.setOnClickListener(v -> startActivity(new Intent(this, StudentViewBranchActivity.class)));

        notificationimgbtn.setOnClickListener(v -> startActivity(new Intent(this, StudentNotificationActivity.class)));
        Course1btn.setOnClickListener(v -> startActivity(new Intent(this, DdsCourseActivity.class)));
        Course2btn.setOnClickListener(v -> startActivity(new Intent(this, HnddsCourseActivity.class)));
        registerbtn.setOnClickListener(v -> startActivity(new Intent(this, CourseRegistrationActivity.class)));

        menuimgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userEmail != null) {
                    Intent intent = new Intent(GuestHomeActivity.this, ProfileActivity.class);
                    intent.putExtra("email", userEmail);
                    startActivity(intent);
                } else {
                    Toast.makeText(GuestHomeActivity.this, "Email not available yet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Fetch the user's email from the "User" collection based on the given username.
     */
    private void fetchUserEmail(String userName) {
        firestore.collection("User")
                .whereEqualTo("userName", userName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots) {
                            userEmail = document.getString("email");
                            if (userEmail != null) {
                                Toast.makeText(this, "Email Found: " + userEmail, Toast.LENGTH_SHORT).show();
                                setupMenuButton(); // Set up the button after email is fetched
                                break;
                            }
                        }
                    } else {
                        Toast.makeText(this, "User not found in collection!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching email!", Toast.LENGTH_SHORT).show());
    }

    private void setupMenuButton() {
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        menuimgbtn.setOnClickListener(v -> {
            Intent intent = new Intent(GuestHomeActivity.this, ProfileActivity.class);
            intent.putExtra("email", userEmail);
            startActivity(intent);
        });
    }




    private void openWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    private void checkPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        isLocationPermissionGranted = true;
                        Toast.makeText(GuestHomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest request, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void checkGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result != ConnectionResult.SUCCESS) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 0,
                    dialogInterface -> Toast.makeText(GuestHomeActivity.this, "User Cancelled Dialog", Toast.LENGTH_SHORT).show());
            if (dialog != null) dialog.show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            startActivity(new Intent(GuestHomeActivity.this, StudentViewCourseActivity.class));
            return true;
        }
        if (itemID == R.id.home) {
            startActivity(new Intent(GuestHomeActivity.this, GuestHomeActivity.class));
            return true;
        }
        if (itemID == R.id.branch) {
            startActivity(new Intent(GuestHomeActivity.this, StudentViewBranchActivity.class));
            return true;
        }
        return false;
    }
}
