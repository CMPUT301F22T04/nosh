package com.example.nosh.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;

/**
 * This class handles the login of a user, it will authenticate with FireBase
 * The user can switch to the register activity if they already have an account
 * @author JulianCamiloGallego
 * @version 1.0
 */
public class Login extends AppCompatActivity {
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView registerPageButton;
    private FirebaseAuth fAuth;

    private class LoginListener implements View.OnClickListener,
            OnCompleteListener<AuthResult> {

        @Override
        public void onClick(View v) {
            if (v.getId() == loginButton.getId()) {
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

                fAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this);
            } else if (v.getId() == registerPageButton.getId()) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        }

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Log.d("LOGIN", "Successfully logged in");

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Log.d("LOGIN ERROR", "Error ! " + task.getException());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registerPageButton = findViewById(R.id.register_page_btn);

        LoginListener loginListener = new LoginListener();

        loginButton.setOnClickListener(loginListener);
        registerPageButton.setOnClickListener(loginListener);
    }
}

