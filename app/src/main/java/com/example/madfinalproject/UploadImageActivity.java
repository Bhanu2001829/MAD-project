package com.example.madfinalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadImageActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 101;
    private static final int CAPTURE_IMAGE = 102;

    private ImageView ivProfilePhoto;
    private ImageButton btnGallery, btnCamera;
    private Button userbtn;
    private FirebaseFirestore firestore;
    private Uri selectedImageUri;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        // Initialize UI elements
        initializeUI();
        setupListeners();
    }

    private void initializeUI() {
        ivProfilePhoto = findViewById(R.id.ivProfilePhoto);
        btnGallery = findViewById(R.id.btnGallery);
        btnCamera = findViewById(R.id.btnCamera);
        userbtn = findViewById(R.id.userbtn);

        firestore = FirebaseFirestore.getInstance();
        userbtn.setEnabled(false); // Initially disable apply button
        // Get the user's email from the intent
        userEmail = getIntent().getStringExtra("email");

    }

    private void setupListeners() {
        btnGallery.setOnClickListener(v -> openGallery());
        btnCamera.setOnClickListener(v -> openCamera());
        userbtn.setOnClickListener(v -> uploadProfileImage());
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                selectedImageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    ivProfilePhoto.setImageBitmap(bitmap);
                    userbtn.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAPTURE_IMAGE) {
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                ivProfilePhoto.setImageBitmap(capturedImage);
                userbtn.setEnabled(true);
            }
        }
    }

    private void uploadProfileImage() {
        if (selectedImageUri == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> imageData = new HashMap<>();
        imageData.put("imageUri", selectedImageUri.toString());
        imageData.put("email", userEmail); // Add the user's email to the image data")

        firestore.collection("images") // "Student" is the Firestore collection name
                .add(imageData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(com.google.firebase.firestore.DocumentReference documentReference) {
                        Toast.makeText(UploadImageActivity.this, "Photo added successfully!", Toast.LENGTH_SHORT).show();
                        Intent uploadImage = new Intent(UploadImageActivity.this, GuestHomeActivity.class);
                        startActivity(uploadImage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadImageActivity.this, "Failed to add user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
