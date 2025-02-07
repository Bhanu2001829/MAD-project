package com.example.madfinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLanguageActivity extends AppCompatActivity {

    private RadioButton englishRadioButton, tamilRadioButton;
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_language);



        // Initialize UI elements
        englishRadioButton = findViewById(R.id.englishRadioButton);
        tamilRadioButton = findViewById(R.id.tamilRadioButton);
        getStartedButton = findViewById(R.id.getStartedButton);

        // Load previously selected language
        loadLanguagePreference();

        // Set click listener for the Get Started button
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguagePreference();
                navigateToNextActivity();
            }
        });
    }

    private void saveLanguagePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (englishRadioButton.isChecked()) {
            editor.putString("language", "English");
        } else if (tamilRadioButton.isChecked()) {
            editor.putString("language", "Tamil");
        }
        editor.apply();
    }

    private void loadLanguagePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "English");
        if (language.equals("English")) {
            englishRadioButton.setChecked(true);
        } else if (language.equals("Tamil")) {
            tamilRadioButton.setChecked(true);
        }
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(SelectLanguageActivity.this, MainFormActivity.class);
        startActivity(intent);
        finish();
    }
}
