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
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;


/**
 * This class handles the registration of a user, it will authenticate with FireBase
 * The user can switch to the login activity if they already have an account
 * @author JulianCamiloGallego, Dekr0
 * @version 1.0
 */
public class Register extends AppCompatActivity {

    private Button registerButton;
    private TextView loginPageButton;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText confirmPassword;

    // Field injection (only be used in Android framework (Activity, Fragment, etc.)
    // where constructor inject cannot be used).

    // Want Dagger to provide an instance of FirebaseAuth from the graph
    @Inject
    FirebaseAuth fAuth;

    private class RegisterListener implements View.OnClickListener,
            OnCompleteListener<AuthResult> {

        @Override
        public void onClick(View v) {

            if (v.getId() == registerButton.getId()) {
                final String email = registerEmail.getText().toString().trim();
                final String password =
                        registerPassword.getText().toString().trim();
                final String passwordConfirmation =
                        confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    registerEmail.setError("email required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    registerPassword.setError("password required");
                    return;
                }

                if (!password.equals(passwordConfirmation)) {
                    registerPassword.setError("passwords do not match");
                    confirmPassword.setError("passwords do not match");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this);
            } else if (v.getId() == loginPageButton.getId()) {
                launchLogin();
            }
        }

        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                Log.d("SIGNUP", "createUserWithEmail:success");

                launchLogin();
            } else {
                Log.w("SIGNUP ERROR",
                        "createUserWithEmail:failure", task.getException());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        fAuth.signOut();

        if (isSignedIn()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make Dagger instantiate @Inject fields in Register
        ((Nosh) getApplicationContext()).getAppComponent().inject(this);
        // fAuth is available

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.register_btn);
        loginPageButton = findViewById(R.id.login_page_btn);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.confirmPassword);

        RegisterListener regListener = new RegisterListener();

        registerButton.setOnClickListener(regListener);
        loginPageButton.setOnClickListener(regListener);
    }

    private boolean isSignedIn() {
        return fAuth.getCurrentUser() != null;
    }

    private void launchLogin() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
