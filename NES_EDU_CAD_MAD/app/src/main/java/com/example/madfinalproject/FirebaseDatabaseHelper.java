package com.example.madfinalproject;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirebaseDatabaseHelper {

    private final FirebaseFirestore firestore;

    public FirebaseDatabaseHelper() {
        // Initialize the Firestore instance
        firestore = FirebaseFirestore.getInstance();
    }

    public void addDocument(String collectionName, Map<String, String> data, Context context) {
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
                    }
                });
    }
}
