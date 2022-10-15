package com.example.nosh.database;

import com.google.firebase.auth.FirebaseAuth;


public class UserAuth {

    private FirebaseAuth fAuth;

    UserAuth() {
        fAuth = FirebaseAuth.getInstance();
    }
}
