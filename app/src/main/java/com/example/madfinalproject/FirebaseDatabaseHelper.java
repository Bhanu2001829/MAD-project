package com.example.madfinalproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirebaseDatabaseHelper {

    private FirebaseFirestore firestore;
    private boolean isConnected = true;  // Tracks connection state
    private static final String TAG = "FirebaseSecurity";  // Log tag for debugging

    public FirebaseDatabaseHelper() {
        connectDatabase();  // Initialize Firestore connection
    }

    // Function to establish database connection
    public void connectDatabase() {
        if (!isConnected) {
            firestore = FirebaseFirestore.getInstance();
            isConnected = true;
            Log.d(TAG, "Database connection restored.");
        }
    }

    // Function to disconnect the database in case of an attack
    public void disconnectDatabase() {
        firestore = null;
        isConnected = false;
        Log.e(TAG, "Database disconnected due to a security threat.");
    }

    // Function to detect anomalies (basic implementation)
    private boolean detectThreat() {
        // Placeholder: Implement real security checks
        // Example: Check if too many failed attempts or suspicious data access occurs
        int suspiciousActivity = (int) (Math.random() * 10); // Simulating random security check

        if (suspiciousActivity > 8) { // Arbitrary condition for threat detection
            Log.e(TAG, "Potential security threat detected! Disconnecting database.");
            return true;
        }
        return false;
    }

    public void addDocument(String collectionName, Map<String, String> data, Context context) {
        if (!isConnected) {
            Toast.makeText(context, "Database is disconnected due to security issues.", Toast.LENGTH_LONG).show();
            return;
        }

        if (detectThreat()) {
            disconnectDatabase();
            Toast.makeText(context, "Security Threat Detected! Database Disconnected.", Toast.LENGTH_LONG).show();
            return;
        }

        firestore.collection(collectionName)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Document added successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error adding document: " + e.getMessage());
                    }
                });
    }
}
