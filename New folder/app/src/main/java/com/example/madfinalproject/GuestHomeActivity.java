package com.example.madfinalproject;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GuestHomeActivity extends AppCompatActivity {

    boolean isLocationPermissionGranted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.guest_home);

        // Create a Method to add permissions
        checkPermission();

        if (isLocationPermissionGranted){
            if (checkGooglePlayServices()){
                Toast.makeText(this,"Google PlayServices Available", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Google PlayServices Not Available", Toast.LENGTH_SHORT).show();
            }
        }


        ImageButton coursebtn = findViewById(R.id.cource_btn);
        ImageButton branchbtn = findViewById(R.id.branch_btn);
        ImageButton registerbtn = findViewById(R.id.register_btn);
        ImageButton menuimgbtn = findViewById(R.id.menuButton);
        ImageButton notificationimgbtn = findViewById(R.id.notificationButton);




        coursebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login Activity
                Intent intent = new Intent(GuestHomeActivity.this, StudentViewCourseActivity.class);
                startActivity(intent);
            }
        });

        menuimgbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        notificationimgbtn.setOnClickListener(v -> {
            // Navigate to Notification Activity
            Intent intent = new Intent(GuestHomeActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        branchbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, StudentViewBranchActivity.class);
            startActivity(intent);
        });
        branchbtn.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(GuestHomeActivity.this, StudentViewBranchActivity.class);
            startActivity(intent);
        });


    }

    private void checkPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                isLocationPermissionGranted = true;
                Toast.makeText( GuestHomeActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(GuestHomeActivity.this, "User Cancelled Dialog", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();
        }


        return false;
    }



}