package com.example.nosh.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText loginEmail;
    EditText loginPassword;
    Button loginButton;
    TextView registerPageButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerPageButton = findViewById(R.id.register_page_btn);

        loginButton.setOnClickListener(v -> {
            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                loginEmail.setError("email required");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                loginPassword.setError("password required");
                return;
            }

            // authenticate the user
            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("LOGIN", "Successfully logged in");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Log.d("LOGIN ERROR", "Error ! " + task.getException().getMessage());
                }
            });

        });

        registerPageButton.setOnClickListener(v -> {
            // take user to the register page
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        });
    }
}

