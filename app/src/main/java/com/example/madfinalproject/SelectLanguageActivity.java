package com.example.madfinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SelectLanguageActivity extends AppCompatActivity {

    private RadioButton englishRadioButton, tamilRadioButton, sinhalaRadioButton;
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLanguagePreference(); // Load saved language before setting content view
        setContentView(R.layout.select_language);

        // Initialize UI elements
        englishRadioButton = findViewById(R.id.englishRadioButton);
        tamilRadioButton = findViewById(R.id.tamilRadioButton);
        sinhalaRadioButton = findViewById(R.id.sinhalaRadioButton);
        getStartedButton = findViewById(R.id.getStartedButton);

        // Load and apply language preference
        setRadioButtonState();

        // Set listeners for language selection
        englishRadioButton.setOnClickListener(v -> changeLanguage("en"));
        tamilRadioButton.setOnClickListener(v -> changeLanguage("ta"));
        sinhalaRadioButton.setOnClickListener(v -> changeLanguage("si"));

        // Set click listener for the Get Started button
        getStartedButton.setOnClickListener(v -> {
            navigateToNextActivity();
        });
    }

    private void changeLanguage(String languageCode) {
        setLocale(languageCode);
        saveLanguagePreference(languageCode);
        recreate(); // Restart activity to apply changes
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    private void saveLanguagePreference(String languageCode) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    private void loadLanguagePreference() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");
        setLocale(language);
    }

    private void setRadioButtonState() {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");
        if (language.equals("en")) {
            englishRadioButton.setChecked(true);
        } else if (language.equals("ta")) {
            tamilRadioButton.setChecked(true);
        }
        else if (language.equals("si")) {
            sinhalaRadioButton.setChecked(true);
        }
    }

    private void navigateToNextActivity() {
        Intent intent = new Intent(SelectLanguageActivity.this, MainFormActivity.class);
        startActivity(intent);
        finish();
    }
}
