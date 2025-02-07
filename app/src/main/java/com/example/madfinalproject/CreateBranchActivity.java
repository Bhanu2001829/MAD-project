package com.example.madfinalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;
import java.util.Map;

public class CreateBranchActivity extends AppCompatActivity {

    private EditText branchNameEditText, branchAddressEditText, postalCodeEditText, contactNumberEditText, locationEditText, emailAddressEditText;
    private FirebaseFirestore firestore;
    boolean isLocationPermissionGranted;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_branch);

        BottomNavigationView bottomNavigationView;
        // Set up bottom navigation to switch Activities
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Create a Method to add permissions
        checkPermission();

        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI elements
        branchNameEditText = findViewById(R.id.branch_name);
        branchAddressEditText = findViewById(R.id.branch_address);
        postalCodeEditText = findViewById(R.id.postal_code);
        contactNumberEditText = findViewById(R.id.branch_contact_number);
        emailAddressEditText = findViewById(R.id.email_address);
        locationEditText = findViewById(R.id.location_url);
        mapView = findViewById(R.id.map_view);

        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        Button addBranchButton = findViewById(R.id.userbtn);
        Button setmapLocation = findViewById(R.id.button);

        // Set button listeners
        menuButton.setOnClickListener(v -> navigateToProfile());
        notificationButton.setOnClickListener(v -> navigateToNotifications());
        addBranchButton.setOnClickListener(v -> addBranch());
        setmapLocation.setOnClickListener(v -> navigateToSetMapLocation());
    }

    private void navigateToProfile() {
        Intent intent = new Intent(CreateBranchActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToNotifications() {
        Intent intent = new Intent(CreateBranchActivity.this, NotificationActivity.class);
        startActivity(intent);
    }

    private void navigateToSetMapLocation() {
        Intent intent = new Intent(CreateBranchActivity.this, SetMapLocationActivity.class);
        startActivityForResult(intent, 1);
    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(CreateBranchActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(CreateBranchActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(CreateBranchActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void addBranch() {
        // Get input values
        String branchName = branchNameEditText.getText().toString().trim();
        String branchAddress = branchAddressEditText.getText().toString().trim();
        String postalCode = postalCodeEditText.getText().toString().trim();
        String contactNumber = contactNumberEditText.getText().toString().trim();
        String emailAddress = emailAddressEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(branchName) || TextUtils.isEmpty(branchAddress) || TextUtils.isEmpty(postalCode) ||
                TextUtils.isEmpty(contactNumber) || TextUtils.isEmpty(emailAddress)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a branch data map
        Map<String, Object> branchData = new HashMap<>();
        branchData.put("branch_id", firestore.collection("branches").document().getId()); // Generate unique ID
        branchData.put("branch_name", branchName);
        branchData.put("branch_address", branchAddress);
        branchData.put("postal_code", postalCode);
        branchData.put("branch_contact_number", contactNumber);
        branchData.put("email_address", emailAddress);
        branchData.put("map_view", location); // Placeholder for future map data

        // Add branch data to Firestore
        firestore.collection("Branch")
                .add(branchData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateBranchActivity.this, "Branch added successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateBranchActivity.this, "Failed to add branch: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isLocationPermissionGranted = true;
                Toast.makeText( CreateBranchActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);

            // Update the locationEditText with the selected location
            String locationString = latitude + "," + longitude;
            locationEditText.setText(locationString);

            // Update the MapView (assuming you have one in your layout)
            // Replace with your actual MapView logic
            updateMapView(latitude, longitude);
        }
    }

    private void updateMapView(double latitude, double longitude) {
        // Code to update MapView with the selected location
        mapView.getMapAsync(googleMap -> {
            LatLng location = new LatLng(latitude, longitude);
            googleMap.clear();
            googleMap.addMarker(new MarkerOptions().position(location).title("Selected Location"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        });
    }


    private void clearFields() {
        branchNameEditText.setText("");
        branchAddressEditText.setText("");
        postalCodeEditText.setText("");
        contactNumberEditText.setText("");
        emailAddressEditText.setText("");
        locationEditText.setText("");
    }
}
