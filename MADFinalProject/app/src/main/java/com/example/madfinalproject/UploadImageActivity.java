package com.example.madfinalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class UploadImageActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;

    private ImageView imageView;
    private ImageButton cameraButton;
    private ImageButton galleryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);

        initializeViews();
        setupListeners();
    }

    /**
     * Initializes the views used in the activity.
     */
    private void initializeViews() {
        imageView = findViewById(R.id.ivProfilePhoto);
        cameraButton = findViewById(R.id.btnCamera);
        galleryButton = findViewById(R.id.btnGallery);

<<<<<<< Updated upstream
        Button applyButton = findViewById(R.id.applybtn);
=======
        Button applyButton = findViewById(R.id.userbtn);
>>>>>>> Stashed changes
        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);

        applyButton.setOnClickListener(v -> navigateTo(RegistrationSuccessfulyActivity.class));
        menuButton.setOnClickListener(v -> navigateTo(ProfileActivity.class));
        notificationButton.setOnClickListener(v -> navigateTo(NotificationActivity.class));
    }

    /**
     * Sets up click listeners for camera and gallery buttons.
     */
    private void setupListeners() {
        cameraButton.setOnClickListener(v -> openCamera());
        galleryButton.setOnClickListener(v -> openGallery());
    }

    /**
     * Navigates to the specified activity.
     *
     * @param targetActivity the target activity class
     */
    private void navigateTo(Class<?> targetActivity) {
        Intent intent = new Intent(this, targetActivity);
        startActivity(intent);
    }

    /**
     * Opens the camera for capturing an image.
     */
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, REQUEST_CAMERA);
        } else {
            showToast("Camera not available");
        }
    }

    /**
     * Opens the gallery for selecting an image.
     */
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            handleImageResult(requestCode, data);
        }
    }

    /**
     * Handles the result from the camera or gallery intent.
     *
     * @param requestCode the request code (camera or gallery)
     * @param data        the intent data containing the image
     */
    private void handleImageResult(int requestCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        } else if (requestCode == REQUEST_GALLERY) {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Failed to load image");
            }
        }
    }

    /**
     * Displays a toast message.
     *
     * @param message the message to display
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
