package com.example.madfinalproject;

import static com.karumi.dexter.listener.SnackbarUtils.show;

import android.Manifest;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Call the method to handle the delayed transition
        switchToMainFormActivityAfterDelay();

        firestore = FirebaseFirestore.getInstance();
        Map<String, String> map = new HashMap<>();

        map.put("name", "Manahara");
        map.put("desc", "test");
        map.put("status", "success");

        @SuppressLint({"NewApi", "LocalSuppress"}) LocalDateTime now = LocalDateTime.now();
        @SuppressLint({"NewApi", "LocalSuppress"}) DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        @SuppressLint({"NewApi", "LocalSuppress"}) String formattedDate = now.format(formatter);

        map.put("date", formattedDate);

        firestore.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
         }
        });


    }


    private void switchToMainFormActivityAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
            startActivity(intent);
            finish(); // Close activity_main activity
        }, DELAY_TIME);
    }
}