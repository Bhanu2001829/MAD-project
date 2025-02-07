package com.example.madfinalproject;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "my_channel";
    private static final int NOTIFICATION_ID = Notification.PRIORITY_MAX;
    private EditText notificationTopicEditText, notificationDetailsEditText;
    private FirebaseFirestore firestore;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.notification);

        firestore = FirebaseFirestore.getInstance();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        createNotificationChannel();

        notificationTopicEditText = findViewById(R.id.notifion_topic);
        notificationDetailsEditText = findViewById(R.id.notification_details);

        ImageButton menuButton = findViewById(R.id.menuButton);
        ImageButton notificationButton = findViewById(R.id.notificationButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button addNotificationButton = findViewById(R.id.addNotificationButtpn);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button seeAllNotificationButton = findViewById(R.id.addNotificationButtpn2);

        addNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

        seeAllNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, AdminSeeNotificationsActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycleView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addNotification() {
        String notificationTopic = notificationTopicEditText.getText().toString().trim();
        String notificationDetails = notificationDetailsEditText.getText().toString().trim();

        if (TextUtils.isEmpty(notificationTopic) || TextUtils.isEmpty(notificationDetails)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("notification_id", firestore.collection("notifications").document().getId());
        notificationData.put("notification_topic", notificationTopic);
        notificationData.put("notification_details", notificationDetails);

        firestore.collection("Notification")
                .add(notificationData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(NotificationActivity.this, "Notification added successfully!", Toast.LENGTH_SHORT).show();
                    showSystemNotification(notificationTopic, notificationDetails);
                    clearFields();
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NotificationActivity.this, "Failed to add notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        notificationTopicEditText.setText("");
        notificationDetailsEditText.setText("");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Thimathi Manahara";
            String channelDescription = "Thimathi Manahara Dissanayake";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showSystemNotification(String title, String message) {
        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_circle_notifications_24wite)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)  // Set High Priority
                .setDefaults(NotificationCompat.DEFAULT_ALL)    // Use system default sounds/vibrations
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // For Heads-Up Notification (Android 10+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Thimathi Manahara",
                    NotificationManager.IMPORTANCE_HIGH // Change to HIGH
            );
            channel.setDescription("Thimathi Manahara Dissanayake");
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}