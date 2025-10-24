package com.aditya.milkman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.aditya.milkman.component.CustomInputField;

public class LoginActivity extends AppCompatActivity {

    public static final String USERNAME = "com.aditya.milkman.USERNAME";
    private CustomInputField usernameField, passwordField;

    public void login(View view) {
        if(!usernameField.getText().toString().trim().isEmpty() && !passwordField.getText().toString().trim().isEmpty()) {
            // Here we need to save the username to the Shared Preferences.
            SharedPreferences sharedPreferences = getSharedPreferences("MILKMAN_DB", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", usernameField.getText().toString());
            editor.apply();

            // Now we have to open the intent.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast toast = Toast
                    .makeText(this, "Bad Request", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (CustomInputField) findViewById(R.id.username);
        passwordField = (CustomInputField) findViewById(R.id.password);
    }
}
