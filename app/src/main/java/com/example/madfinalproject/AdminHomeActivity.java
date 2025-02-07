package com.example.madfinalproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class AdminHomeActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    boolean isLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home); // Make sure the layout name is correct

        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setSelectedItemId(R.id.course);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setSelectedItemId(R.id.branch);
        bottomNavigationView.setOnItemSelectedListener(this::onNavigationItemSelected);

        // Create a Method to check permissions and accept them
        checkPermission();

        if (isLocationPermissionGranted) {
            if (checkGooglePlayServices()) {
                Toast.makeText(this, "Google PlayServices Available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Google PlayServices Not Available", Toast.LENGTH_SHORT).show();
            }
        }


        // Initialize ImageButtons
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        ImageButton userbtn = findViewById(R.id.user_btn);
        ImageButton addAdminbtn = findViewById(R.id.add_user_btn);
        ImageButton addcourcebtn = findViewById(R.id.add_cource_btn);
        ImageButton createbranchbtn = findViewById(R.id.create_branch_btn);
        ImageButton courcebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.branch_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);
        ImageButton view = findViewById(R.id.weblinkbtn);
        ImageButton googleClassRoom = findViewById(R.id.gClassRoom);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOfficialWebsite();
                // Call the method to open the URL
            }
        });

        googleClassRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGClasslWebsite();
                // Call the method to open the URL
            }
        });

        addAdminbtn.setOnClickListener(v -> {
            // Navigate to View Student Activity
            Intent intent = new Intent(AdminHomeActivity.this, AddUserActivity.class);
            startActivity(intent);
        });
        // Setting onClickListeners for ImageButtons
        userbtn.setOnClickListener(v -> {
            // Navigate to View Student Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewStudentActivity.class);
            startActivity(intent);
        });

        addcourcebtn.setOnClickListener(v -> {
            // Navigate to Add Course Activity
            Intent intent = new Intent(AdminHomeActivity.this, AddCourseActivity.class);
            startActivity(intent);
        });

        createbranchbtn.setOnClickListener(v -> {
            // Navigate to Create Branch Activity
            Intent intent = new Intent(AdminHomeActivity.this, CreateBranchActivity.class);
            startActivity(intent);
        });

        courcebtn.setOnClickListener(v -> {
            // Navigate to View Course Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewCourseActivity.class);
            startActivity(intent);
        });

        branchbtn.setOnClickListener(v -> {
            // Navigate to View Branch Activity
            Intent intent = new Intent(AdminHomeActivity.this, ViewBranchActivity.class);
            startActivity(intent);
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(AdminHomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(AdminHomeActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

    }

    @SuppressLint("NonConstantResourceId")
    private boolean onNavigationItemSelected(MenuItem item) {
        int itemID = item.getItemId();

        if (itemID == R.id.course) {
            Intent intent = new Intent(AdminHomeActivity.this, ViewCourseActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.home) {
            Intent intent = new Intent(AdminHomeActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            return true;
        }
        if (itemID == R.id.branch) {
            Intent intent = new Intent(AdminHomeActivity.this, ViewBranchActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    // Method to open the NIBM website
    public void openOfficialWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.nibm.lk"));
        startActivity(browserIntent); // Start the browser or web-view
    }

    public void openGClasslWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://classroom.google.com/u/0/"));
        startActivity(browserIntent); // Start the browser or web-view
    }

    private boolean checkGooglePlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (result == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(result)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, result, 0, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {

                    Toast.makeText(AdminHomeActivity.this, "User Cancelled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            assert dialog != null;
            dialog.show();
        }


        return false;
    }


    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isLocationPermissionGranted = true;
                Toast.makeText(AdminHomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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



}
