package com.example.madfinalproject;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageButton;
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

    boolean isLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home); // Make sure the layout name is correct

        // Create a Method to check permissions and accept them
        checkPermission();

        if (isLocationPermissionGranted){
            if (checkGooglePlayServices()){
                Toast.makeText(this,"Google PlayServices Available", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Google PlayServices Not Available", Toast.LENGTH_SHORT).show();
            }
        }

        // Initialize ImageButtons
        ImageButton userbtn = findViewById(R.id.user_btn);
        ImageButton addcourcebtn = findViewById(R.id.add_cource_btn);
        ImageButton createbranchbtn = findViewById(R.id.create_branch_btn);
        ImageButton courcebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.branch_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);

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

    private boolean checkGooglePlayServices() {

        GoogleApiAvailability googleApiAvailability =GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if(result == ConnectionResult.SUCCESS){
            return true;
        }
        else if(googleApiAvailability.isUserResolvableError(result)){
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

    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isLocationPermissionGranted = true;
                Toast.makeText( AdminHomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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
