package com.example.madfinalproject;

import android.os.Bundle;
<<<<<<< Updated upstream
=======

import androidx.annotation.NonNull;
>>>>>>> Stashed changes
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.os.Handler;
<<<<<<< Updated upstream
=======
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
>>>>>>> Stashed changes

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 3000; // 3 seconds delay
<<<<<<< Updated upstream
=======
    FirebaseFirestore firestore;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Call the method to handle the delayed transition
        switchToMainFormActivityAfterDelay();
<<<<<<< Updated upstream
    }

    /**
     * Adds a delay before transitioning to the next activity (activity_main).
     */
=======

        firestore = FirebaseFirestore.getInstance();
        Map<String, String> map = new HashMap<>();

        map.put("name", "Chamuditha");
        map.put("desc", "test");
        map.put("status", "success");

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


>>>>>>> Stashed changes
    private void switchToMainFormActivityAfterDelay() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
            startActivity(intent);
            finish(); // Close activity_main activity
        }, DELAY_TIME);
    }
}