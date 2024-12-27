package com.example.maddashboard;  // Replace with your package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // Make sure this matches your layout file name

        TextView forgetPwd = findViewById(R.id.txt_forget_pwd);
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to open the forget password screen
                Intent intent = new Intent(LoginActivity.this, FogetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}
