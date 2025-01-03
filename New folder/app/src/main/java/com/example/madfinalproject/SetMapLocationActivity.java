package com.example.madfinalproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SetMapLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private ImageView imageViewSearch;
    private EditText input_location;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationClient;
    private Button setLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_map_location);

        imageViewSearch = findViewById(R.id.imageViewSearch);
        input_location = findViewById(R.id.input_location);
        setLocation = findViewById(R.id.button);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycleView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setLocation.setOnClickListener(v -> {
            if (googleMap != null) {
                LatLng selectedLocation = googleMap.getCameraPosition().target; // Get center of the map
                Intent resultIntent = new Intent();
                resultIntent.putExtra("latitude", selectedLocation.latitude);
                resultIntent.putExtra("longitude", selectedLocation.longitude);
                setResult(RESULT_OK, resultIntent);
                finish(); // Close the activity and return to CreateBranchActivity
            } else {
                Toast.makeText(SetMapLocationActivity.this, "Please select a location first", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewSearch.setOnClickListener(v -> {
            String location = input_location.getText().toString().trim();
            if (location.isEmpty()) {
                Toast.makeText(SetMapLocationActivity.this, "Type any Location Name", Toast.LENGTH_SHORT).show();
            } else {
                searchLocation(location);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        googleMap.setMyLocationEnabled(true);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(currentLatLng)
                        .title("You are here");

                googleMap.addMarker(markerOptions);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
                googleMap.getUiSettings().setAllGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);


                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        CheckGPS();
                        return true;
                    }
                });
            }
        });
    }

    private void GetCurrentUpdates(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(SetMapLocationActivity.this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Toast.makeText(SetMapLocationActivity.this, "Location"+locationResult.getLastLocation().getLongitude()+" : "+locationResult.getLastLocation().getLatitude(), Toast.LENGTH_SHORT).show();
            }
        }, Looper.getMainLooper());
    }


    private void CheckGPS() {
         locationRequest = LocationRequest.create();
         locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
         locationRequest.setInterval(5000);
         locationRequest.setFastestInterval(3000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        Task<LocationSettingsResponse> locationSettingsResponseTask = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        locationSettingsResponseTask.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse request = task.getResult(ApiException.class);
                    if (request != null) {
                        Toast.makeText(SetMapLocationActivity.this, "GPS is Enabled", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SetMapLocationActivity.this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
                    }
                } catch (ApiException e) {
                    if (e.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED){
                        ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                        try {
                            resolvableApiException.startResolutionForResult(SetMapLocationActivity.this, 101);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                    else if (e.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                        Toast.makeText(SetMapLocationActivity.this, "GPS Settings is not available", Toast.LENGTH_SHORT).show();
                    }

                    else{
                        Toast.makeText(SetMapLocationActivity.this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        GetCurrentUpdates();
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(SetMapLocationActivity.this, "GPS is Enabled", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(SetMapLocationActivity.this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(SetMapLocationActivity.this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void searchLocation(String location) {
        Geocoder geocoder = new Geocoder(SetMapLocationActivity.this, Locale.getDefault());
        try {
            List<Address> listAddress = geocoder.getFromLocationName(location, 1);
            if (listAddress != null && !listAddress.isEmpty()) {
                LatLng latLng = new LatLng(listAddress.get(0).getLatitude(), listAddress.get(0).getLongitude());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(location);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                googleMap.addMarker(markerOptions);
            } else {
                Toast.makeText(SetMapLocationActivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(SetMapLocationActivity.this, "Error retrieving location. Check network or try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mapmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.noneMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }
        if(item.getItemId() == R.id.satelliteMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if(item.getItemId() == R.id.normalMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if(item.getItemId() == R.id.hybridMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        if(item.getItemId() == R.id.terrainMap){
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (googleMap != null) {
                    onMapReady(googleMap);
                }
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
