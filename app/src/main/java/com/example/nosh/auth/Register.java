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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText registerEmail;
    EditText registerPassword;
    EditText confirmPassword;
    Button registerButton;
    TextView loginPageButton;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
        confirmPassword = findViewById(R.id.confirmPassword);
        registerButton = findViewById(R.id.register_btn);
        loginPageButton = findViewById(R.id.login_page_btn);

        /*
        // uncomment when you wanna stay signed in
        if (fAuth.getCurrentUser() != null) {
            // if the user is already logged in, start main activity
            Intent intent = new Intent(getApplicationContext(),
                    MainActivity.class);
            startActivity(intent);
        }
         */

        registerButton.setOnClickListener(v -> {
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

            // register the user in firebase
            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("USER CREATED", "User created successfully");

                    userID = fAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fStore.collection(
                            "users").document(userID);
                    Map<String, Object> user = new HashMap<>();
                    user.put("email", email);
                    user.put("password", password);
                    documentReference.set(user).addOnSuccessListener(aVoid ->
                            Log.d("SUCCESS", "User Profile is created for " + userID))
                            .addOnFailureListener(e -> Log.d("FAILURE", e.toString()));
                    startActivity(new Intent(getApplicationContext(),
                            MainActivity.class));
                } else {
                    Log.d("ERROR", "Error ! " + task.getException().getMessage());
                }
            });
        });

        loginPageButton.setOnClickListener(v -> {
            // take user to the login page
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

    }

}
