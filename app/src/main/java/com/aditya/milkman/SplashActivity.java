package com.aditya.milkman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {

            SharedPreferences sharedPreferences = getSharedPreferences("MILKMAN_DB", Context.MODE_PRIVATE);
            String user = sharedPreferences.getString("user", null);

            if (user != null && !user.isEmpty()) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, 3000);
    }
    
}
